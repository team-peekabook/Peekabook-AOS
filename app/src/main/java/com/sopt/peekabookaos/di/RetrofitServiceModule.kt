package com.sopt.peekabookaos.di

import com.sopt.peekabookaos.data.service.DetailService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object RetrofitServiceModule {
    @Provides
    fun providesDetailService(retrofit: Retrofit): DetailService =
        retrofit.create(DetailService::class.java)

