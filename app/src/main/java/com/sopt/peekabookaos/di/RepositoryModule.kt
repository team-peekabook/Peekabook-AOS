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
import com.sopt.peekabookaos.data.repository.ShelfRepository
import com.sopt.peekabookaos.data.repository.ShelfRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
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

    @Provides
    @Singleton
    fun providesShelfRepository(
        shelfRepositoryImpl: ShelfRepositoryImpl
    ): ShelfRepository = shelfRepositoryImpl
}
