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
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {
    private const val CONTENT_TYPE = "Content-Type"
    private const val JSON = "application/json"

    @Provides
    @Singleton
    fun providesInterceptor(): Interceptor = Interceptor { chain ->
        with(chain) {
            proceed(
                request().newBuilder().addHeader(CONTENT_TYPE, JSON).build()
            )
        }
    }

    @Provides
    @Singleton
    fun providesOkHttpClient(interceptor: Interceptor): OkHttpClient =
        OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS).readTimeout(10, TimeUnit.SECONDS)
            .addInterceptor(interceptor).addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            ).build()

    @Provides
    @Singleton
    fun providesAuthRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder().baseUrl(BuildConfig.BASE_URI).client(okHttpClient).addConverterFactory(
            Json.asConverterFactory("application/json".toMediaType())
        ).build()
}
