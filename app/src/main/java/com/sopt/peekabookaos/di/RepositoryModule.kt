package com.sopt.peekabookaos.di

import com.sopt.peekabookaos.data.repository.AuthRepositoryImpl
import com.sopt.peekabookaos.data.repository.BlockRepositoryImpl
import com.sopt.peekabookaos.data.repository.BookRepositoryImpl
import com.sopt.peekabookaos.data.repository.DetailRepositoryImpl
import com.sopt.peekabookaos.data.repository.NaverRepositoryImpl
import com.sopt.peekabookaos.data.repository.NotificationRepositoryImpl
import com.sopt.peekabookaos.data.repository.RecommendRepositoryImpl
import com.sopt.peekabookaos.data.repository.RefreshRepositoryImpl
import com.sopt.peekabookaos.data.repository.SearchRepositoryImpl
import com.sopt.peekabookaos.data.repository.ShelfRepositoryImpl
import com.sopt.peekabookaos.domain.repository.AuthRepository
import com.sopt.peekabookaos.domain.repository.BlockRepository
import com.sopt.peekabookaos.domain.repository.BookRepository
import com.sopt.peekabookaos.domain.repository.DetailRepository
import com.sopt.peekabookaos.domain.repository.NaverRepository
import com.sopt.peekabookaos.domain.repository.NotificationRepository
import com.sopt.peekabookaos.domain.repository.RecommendRepository
import com.sopt.peekabookaos.domain.repository.RefreshRepository
import com.sopt.peekabookaos.domain.repository.SearchRepository
import com.sopt.peekabookaos.domain.repository.ShelfRepository
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
    abstract fun bindToRefreshRepository(
        refreshRepositoryImpl: RefreshRepositoryImpl
    ): RefreshRepository

    @Binds
    @Singleton
    abstract fun bindToAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl
    ): AuthRepository

    @Binds
    @Singleton
    abstract fun bindToBlockRepository(
        blockRepositoryImpl: BlockRepositoryImpl
    ): BlockRepository

    @Binds
    @Singleton
    abstract fun bindToSearchRepository(
        searchRepositoryImpl: SearchRepositoryImpl
    ): SearchRepository

    @Binds
    @Singleton
    abstract fun bindToCreateUpdateRepository(
        bookRepositoryImpl: BookRepositoryImpl
    ): BookRepository

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
