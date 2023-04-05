package com.sopt.peekabookaos.di

import com.sopt.peekabookaos.data.repository.*
import com.sopt.peekabookaos.domain.repository.*
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
    abstract fun bindToAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl
    ): AuthRepository

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

    @Binds
    @Singleton
    abstract fun bindToUserInputRepository(
        userInputRepositoryImpl: UserInputRepositoryImpl
    ): UserInputRepository
}
