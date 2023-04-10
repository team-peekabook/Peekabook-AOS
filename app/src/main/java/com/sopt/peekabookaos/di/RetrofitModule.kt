package com.sopt.peekabookaos.di

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Handler
import android.os.Looper
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

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {
    private val json = Json { ignoreUnknownKeys = true }
    private const val CONTENT_TYPE = "Content-Type"
    private const val APPLICATION_JSON = "application/json"
    private const val BEARER = "Bearer "
    private const val ACCESS_TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOjE4LCJpYXQiOjE2ODA3OTQzMzcsImV4cCI6MTY4MTIyNjMzN30.7riPJ1yQjnB0uu3sMLf4WLqo4NlLQRndeQvRIUOUgls"
    private const val EXPIRED_TOKEN = 401
    private const val SERVER_ERROR = 500

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class PeekaType

    @PeekaType
    @Provides
    fun providesPeekaInterceptor(
        @ApplicationContext context: Context,
        localPref: SharedPreferences,
        refreshRepository: RefreshRepository,
        localTokenDataSource: LocalTokenDataSource
    ): Interceptor = Interceptor { chain ->
        if (!context.isNetworkConnected()) {
            context.startActivity(
                Intent(context, NetworkErrorActivity::class.java).apply {
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                }
            )
        }
        val request = chain.request()
        var response = chain.proceed(
            request
                .newBuilder()
                .addHeader(CONTENT_TYPE, APPLICATION_JSON)
                .addHeader(ACCESS_TOKEN, BEARER + localTokenDataSource.accessToken)
                .build()
        )
        when (response.code) {
            EXPIRED_TOKEN -> {
                runBlocking {
                    refreshRepository.getRefreshToken()
                        .onSuccess {
                            response = chain.proceed(
                                request
                                    .newBuilder()
                                    .addHeader(CONTENT_TYPE, APPLICATION_JSON)
                                    .addHeader(
                                        ACCESS_TOKEN,
                                        BEARER + localTokenDataSource.accessToken
                                    )
                                    .build()
                            )
                        }.onFailure { throwable ->
                            Timber.e("토큰 갱신 실패 ${throwable.message}")
                            if (throwable is HttpException) {
                                when (throwable.code()) {
                                    EXPIRED_TOKEN -> {
                                        with(localPref.edit()) {
                                            clear()
                                            commit()
                                        }
                                        Handler(Looper.getMainLooper()).post {
                                            ToastMessageUtil.showToast(
                                                context,
                                                context.getString(R.string.refresh_error)
                                            )
                                            context.startActivity(
                                                Intent(
                                                    context,
                                                    LoginActivity::class.java
                                                ).apply { addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK) }
                                            )
                                        }
                                    }
                                }
                            }
                        }
                }
            }
            SERVER_ERROR -> {
                Handler(Looper.getMainLooper()).post {
                    ToastMessageUtil.showToast(
                        context,
                        context.getString(R.string.sever_500_error_msg)
                    )
                }
            }
        }
        response
    }

    @PeekaType
    @Provides
    fun providesPeekaOkHttpClient(@PeekaType interceptor: Interceptor): OkHttpClient =
        OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .addInterceptor(interceptor)
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            ).build()

    @PeekaType
    @Provides
    fun providesPeekaRetrofit(@PeekaType okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URI)
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
}
