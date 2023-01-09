package com.sopt.peekabookaos.di

import com.sopt.peekabookaos.data.service.DetailService
import com.sopt.peekabookaos.data.service.NotificationService
import com.sopt.peekabookaos.data.service.RecommendService
import com.sopt.peekabookaos.data.service.SearchService
import com.sopt.peekabookaos.data.service.ShelfService
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

    @Provides
    fun providesNotificationService(retrofit: Retrofit): NotificationService =
        retrofit.create(NotificationService::class.java)

    @Provides
    fun providesRecommendService(retrofit: Retrofit): RecommendService =
        retrofit.create(RecommendService::class.java)

    @Provides
    fun providesSearchService(retrofit: Retrofit): SearchService =
        retrofit.create(SearchService::class.java)

    @Provides
    fun providesShelfService(retrofit: Retrofit): ShelfService =
        retrofit.create(ShelfService::class.java)

