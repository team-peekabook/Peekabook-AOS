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
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindToSearchRepository(
        searchRepositoryImpl: SearchRepositoryImpl
    ): SearchRepository

    @Binds
    @Singleton
    abstract fun bindToCreateUpdateRepository(
        createUpdateRepositoryImpl: CreateUpdateRepositoryImpl
    ): CreateUpdateRepository

    @Binds
    @Singleton
    abstract fun bindToDetailRepository(
        detailRepositoryImpl: DetailRepositoryImpl
    ): DetailRepository

    @Binds
    @Singleton
    abstract fun bindToNotificationRepository(
        notificationRepositoryImpl: NotificationRepositoryImpl
    ): NotificationRepository

    @Binds
    @Singleton
    abstract fun bindToRecommendRepository(
        recommendRepositoryImpl: RecommendRepositoryImpl
    ): RecommendRepository

    @Binds
    @Singleton
    abstract fun bindToShelfRepository(
        shelfRepositoryImpl: ShelfRepositoryImpl
    ): ShelfRepository

    @Binds
    @Singleton
    abstract fun bindToNaverRepository(
        naverRepositoryImpl: NaverRepositoryImpl
    ): NaverRepository
}
