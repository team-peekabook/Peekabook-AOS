package com.sopt.peekabookaos.di

import com.sopt.peekabookaos.data.service.AuthService
import com.sopt.peekabookaos.data.service.BlockService
import com.sopt.peekabookaos.data.service.BookService
import com.sopt.peekabookaos.data.service.DetailService
import com.sopt.peekabookaos.data.service.NaverService
import com.sopt.peekabookaos.data.service.NotificationService
import com.sopt.peekabookaos.data.service.RecommendService
import com.sopt.peekabookaos.data.service.SearchService
import com.sopt.peekabookaos.data.service.ShelfService
import com.sopt.peekabookaos.di.NaverRetrofitModule.NaverType
import com.sopt.peekabookaos.di.RetrofitModule.PeekaType
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object RetrofitServiceModule {
    @Provides
    fun providesAuthService(@PeekaType retrofit: Retrofit): AuthService =
        retrofit.create(AuthService::class.java)

    @Provides
    fun providesBlockService(@PeekaType retrofit: Retrofit): BlockService =
        retrofit.create(BlockService::class.java)

    @Provides
    fun providesDetailService(@PeekaType retrofit: Retrofit): DetailService =
        retrofit.create(DetailService::class.java)

    @Provides
    fun providesNotificationService(@PeekaType retrofit: Retrofit): NotificationService =
        retrofit.create(NotificationService::class.java)

    @Provides
    fun providesRecommendService(@PeekaType retrofit: Retrofit): RecommendService =
        retrofit.create(RecommendService::class.java)

    @Provides
    fun providesSearchService(@PeekaType retrofit: Retrofit): SearchService =
        retrofit.create(SearchService::class.java)

    @Provides
    fun providesShelfService(@PeekaType retrofit: Retrofit): ShelfService =
        retrofit.create(ShelfService::class.java)

    @Provides
    fun providesCreateUpdateService(@PeekaType retrofit: Retrofit): BookService =
        retrofit.create(BookService::class.java)

    @Provides
    fun providesNaverService(@NaverType retrofit: Retrofit): NaverService =
        retrofit.create(NaverService::class.java)
}
