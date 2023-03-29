package com.sopt.peekabookaos.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.sopt.peekabookaos.BuildConfig
import com.sopt.peekabookaos.data.source.local.LocalPrefDataSource
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

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {
    private val json = Json { ignoreUnknownKeys = true }
    private const val CONTENT_TYPE = "Content-Type"
    private const val APPLICATION_JSON = "application/json"
    private const val AUTH = "auth"
    private const val USER_ID = "1"
    private const val BEARER = "Bearer "
    private const val ACCESS_TOKEN = "accessToken"

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class PeekaType

    @PeekaType
    @Provides
    fun providesPeekaInterceptor(localPrefDataSource: LocalPrefDataSource): Interceptor =
        Interceptor { chain ->
            with(chain) {
                proceed(
                    request()
                        .newBuilder()
                        .addHeader(CONTENT_TYPE, APPLICATION_JSON)
                        /** 로그인, 회원정보입력 구현할 때에는 45번째 줄을, 나머지는 44번째 줄을 살려서 사용하세요! */
                        // .addHeader(AUTH, USER_ID)
                        .addHeader(ACCESS_TOKEN, BEARER + localPrefDataSource.accessToken)
                        .build()
                )
            }
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
