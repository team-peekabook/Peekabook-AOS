package com.sopt.peekabookaos.di

import com.sopt.peekabookaos.data.repository.CreateUpdateRepository
import com.sopt.peekabookaos.data.repository.CreateUpdateRepositoryImpl
import com.sopt.peekabookaos.data.repository.DetailRepository
import com.sopt.peekabookaos.data.repository.DetailRepositoryImpl
import com.sopt.peekabookaos.data.repository.NaverRepository
import com.sopt.peekabookaos.data.repository.NaverRepositoryImpl
import com.sopt.peekabookaos.data.repository.NotificationRepository
import com.sopt.peekabookaos.data.repository.NotificationRepositoryImpl
import com.sopt.peekabookaos.data.repository.RecommendRepository
import com.sopt.peekabookaos.data.repository.RecommendRepositoryImpl
import com.sopt.peekabookaos.data.repository.SearchRepositoryImpl
import com.sopt.peekabookaos.data.repository.ShelfRepository
import com.sopt.peekabookaos.data.repository.ShelfRepositoryImpl
import com.sopt.peekabookaos.domain.repository.SearchRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    fun providesSearchRepository(
        searchRepositoryImpl: SearchRepositoryImpl
    ): SearchRepository = searchRepositoryImpl

    @Provides
    fun providesCreateUpdateRepository(
        createUpdateRepositoryImpl: CreateUpdateRepositoryImpl
    ): CreateUpdateRepository = createUpdateRepositoryImpl

    @Provides
    fun providesDetailRepository(
        detailRepositoryImpl: DetailRepositoryImpl
    ): DetailRepository = detailRepositoryImpl

    @Provides
    fun providesNotificationRepository(
        notificationRepositoryImpl: NotificationRepositoryImpl
    ): NotificationRepository = notificationRepositoryImpl

    @Provides
    fun providesRecommendRepository(
        recommendRepositoryImpl: RecommendRepositoryImpl
    ): RecommendRepository = recommendRepositoryImpl

    @Provides
    fun providesShelfRepository(
        shelfRepositoryImpl: ShelfRepositoryImpl
    ): ShelfRepository = shelfRepositoryImpl

    @Provides
    fun providesNaverRepository(
        naverRepositoryImpl: NaverRepositoryImpl
    ): NaverRepository = naverRepositoryImpl
}
