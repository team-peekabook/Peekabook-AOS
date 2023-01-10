package com.sopt.peekabookaos.di

import com.sopt.peekabookaos.data.repository.CreateUpdateRepository
import com.sopt.peekabookaos.data.repository.CreateUpdateRepositoryImpl
import com.sopt.peekabookaos.data.repository.DetailRepository
import com.sopt.peekabookaos.data.repository.DetailRepositoryImpl
import com.sopt.peekabookaos.data.repository.NotificationRepository
import com.sopt.peekabookaos.data.repository.NotificationRepositoryImpl
import com.sopt.peekabookaos.data.repository.RecommendRepository
import com.sopt.peekabookaos.data.repository.RecommendRepositoryImpl
import com.sopt.peekabookaos.data.repository.SearchRepository
import com.sopt.peekabookaos.data.repository.SearchRepositoryImpl
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule
object RepositoryModule {
    @Provides
    @Singleton
    fun providesSearchRepository(
        searchRepositoryImpl: SearchRepositoryImpl
    ): SearchRepository = searchRepositoryImpl

    @Provides
    @Singleton
    fun providesCreateUpdateRepository(
        createUpdateRepositoryImpl: CreateUpdateRepositoryImpl
    ): CreateUpdateRepository = createUpdateRepositoryImpl

    @Provides
    @Singleton
    fun providesDetailRepository(
        detailRepositoryImpl: DetailRepositoryImpl
    ): DetailRepository = detailRepositoryImpl

    @Provides
    @Singleton
    fun providesNotificationRepository(
        notificationRepositoryImpl: NotificationRepositoryImpl
    ): NotificationRepository = notificationRepositoryImpl

    @Provides
    @Singleton
    fun providesRecommendRepository(
        recommendRepositoryImpl: RecommendRepositoryImpl
    ): RecommendRepository = recommendRepositoryImpl

