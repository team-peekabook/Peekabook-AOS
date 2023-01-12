package com.sopt.peekabookaos.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.sopt.peekabookaos.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NaverRetrofitModule {
    private val json = Json { ignoreUnknownKeys = true }
    private const val NAVER_CLIENT_ID = "X-Naver-Client-Id"
    private const val NAVER_CLIENT_SECRET = "X-Naver-Client-Secret"

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class NaverType

    @NaverType
    @Provides
    fun providesNaverInterceptor(): Interceptor = Interceptor { chain ->
        with(chain) {
            proceed(
                request().newBuilder()
                    .addHeader(NAVER_CLIENT_ID, BuildConfig.CLIENT_ID)
                    .addHeader(NAVER_CLIENT_SECRET, BuildConfig.CLIENT_SECRET)
                    .build()
            )
        }
    }

    @NaverType
    @Provides
    fun providesNaverOkHttpClient(@NaverType interceptor: Interceptor): OkHttpClient =
        OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS).readTimeout(10, TimeUnit.SECONDS)
            .addInterceptor(interceptor).addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            ).build()

    @NaverType
    @Provides
    fun providesNaverRetrofit(@NaverType okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder().baseUrl(BuildConfig.NAVER_URL).client(okHttpClient)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
}
