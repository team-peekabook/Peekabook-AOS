package com.sopt.peekabookaos.di

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Handler
import android.os.Looper
import androidx.core.content.edit
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.sopt.peekabookaos.BuildConfig
import com.sopt.peekabookaos.R
import com.sopt.peekabookaos.data.source.local.LocalTokenDataSource
import com.sopt.peekabookaos.domain.repository.RefreshRepository
import com.sopt.peekabookaos.presentation.login.LoginActivity
import com.sopt.peekabookaos.presentation.networkError.NetworkErrorActivity
import com.sopt.peekabookaos.util.ToastMessageUtil
import com.sopt.peekabookaos.util.extensions.isNetworkConnected
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.HttpException
import retrofit2.Retrofit
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {
    private val json = Json { ignoreUnknownKeys = true }
    private const val CONTENT_TYPE = "Content-Type"
    private const val APPLICATION_JSON = "application/json"
    private const val BEARER = "Bearer "
    private const val ACCESS_TOKEN = "accessToken"
    private const val BAD_REQUEST = 400
    private const val EXPIRED_TOKEN = 401
    private const val SERVER_ERROR = 500
    private const val RETRY_HEADER = "X-Retry"

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class PeekaType

    /** ------------------------------------------------------------------
     *  Interceptor : 토큰 헤더 + 401 재시도 + 500 토스트 + 네트워크 체크
     * ------------------------------------------------------------------ */
    @PeekaType
    @Singleton
    @Provides
    fun providesPeekaInterceptor(
        @ApplicationContext context: Context,
        localPref: SharedPreferences,
        refreshRepository: RefreshRepository,
        localTokenDataSource: LocalTokenDataSource
    ): Interceptor = Interceptor { chain ->
        /* 오프라인이면 에러 액티비티 띄우기 */
        if (!context.isNetworkConnected()) {
            context.startActivity(
                Intent(context, NetworkErrorActivity::class.java).apply {
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                }
            )
        }

        val originalRequest = chain.request()
        val initialRequest = originalRequest.newBuilder()
            .addHeader(CONTENT_TYPE, APPLICATION_JSON)
            .addHeader(ACCESS_TOKEN, BEARER + localTokenDataSource.accessToken)
            .build()

        var response = chain.proceed(initialRequest)

        when (response.code) {
            EXPIRED_TOKEN -> {
                // 이미 리트라이한 요청이면 더 이상 시도 X
                if (originalRequest.header(RETRY_HEADER) != null) {
                    return@Interceptor response
                }

                response.close() // ★ 이전 응답 확실히 닫기

                val refreshResult = runBlocking { refreshRepository.getRefreshToken() }

                if (refreshResult.isSuccess) {
                    // 새 토큰으로 다시 요청 — 재시도 플래그 달기
                    val newRequest = originalRequest.newBuilder()
                        .addHeader(CONTENT_TYPE, APPLICATION_JSON)
                        .addHeader(ACCESS_TOKEN, BEARER + localTokenDataSource.accessToken)
                        .addHeader(RETRY_HEADER, "true")
                        .build()
                    response = chain.proceed(newRequest)

                    /* 재요청 결과가 500이면 토스트 */
                    if (response.code == SERVER_ERROR) {
                        showServerErrorToast(context)
                    }
                } else {
                    /* 토큰 갱신 실패 */
                    handleRefreshFail(context, localPref, refreshResult.exceptionOrNull())
                }
            }

            SERVER_ERROR -> showServerErrorToast(context)
        }

        response
    }

    /** ------------------------------------------------------------------
     *  OkHttpClient
     * ------------------------------------------------------------------ */
    @PeekaType
    @Singleton
    @Provides
    fun providesPeekaOkHttpClient(@PeekaType interceptor: Interceptor): OkHttpClient =
        OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .addInterceptor(interceptor)
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level =
                        if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
                }
            ).build()

    /** ------------------------------------------------------------------
     *  Retrofit
     * ------------------------------------------------------------------ */
    @PeekaType
    @Singleton
    @Provides
    fun providesPeekaRetrofit(@PeekaType okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URI)
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()

    /** ------------------------------------------------------------------
     *  헬퍼 함수들
     * ------------------------------------------------------------------ */
    private fun showServerErrorToast(context: Context) {
        Handler(Looper.getMainLooper()).post {
            ToastMessageUtil.showToast(
                context,
                context.getString(R.string.sever_500_error_msg)
            )
        }
    }

    private fun handleRefreshFail(
        context: Context,
        localPref: SharedPreferences,
        throwable: Throwable?
    ) {
        Timber.e(throwable, "토큰 갱신 실패")
        if (throwable is HttpException &&
            (throwable.code() == BAD_REQUEST || throwable.code() == EXPIRED_TOKEN)
        ) {
            /* 로컬 데이터 초기화 */
            localPref.edit { clear() }

            /* 토스트 & 로그인 화면 */
            Handler(Looper.getMainLooper()).post {
                ToastMessageUtil.showToast(
                    context,
                    context.getString(R.string.refresh_error)
                )
                context.startActivity(
                    Intent(context, LoginActivity::class.java).apply {
                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    }
                )
            }
        }
    }
}
