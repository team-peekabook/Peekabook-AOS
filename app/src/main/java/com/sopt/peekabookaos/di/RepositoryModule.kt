package com.sopt.peekabookaos.di

import com.sopt.peekabookaos.data.repository.CreateUpdateRepository
import com.sopt.peekabookaos.data.repository.CreateUpdateRepositoryImpl
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

