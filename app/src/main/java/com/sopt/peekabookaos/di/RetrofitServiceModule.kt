package com.sopt.peekabookaos.di

import com.sopt.peekabookaos.data.service.AuthService
import com.sopt.peekabookaos.data.service.BlockService
import com.sopt.peekabookaos.data.service.BookService
import com.sopt.peekabookaos.data.service.DetailService
import com.sopt.peekabookaos.data.service.ForcedUpdateService
import com.sopt.peekabookaos.data.service.MyPageService
import com.sopt.peekabookaos.data.service.NaverService
import com.sopt.peekabookaos.data.service.NotificationService
import com.sopt.peekabookaos.data.service.RecommendService
import com.sopt.peekabookaos.data.service.RefreshService
import com.sopt.peekabookaos.data.service.ReportService
import com.sopt.peekabookaos.data.service.SearchService
import com.sopt.peekabookaos.data.service.ShelfService
import com.sopt.peekabookaos.data.service.UserInputService
import com.sopt.peekabookaos.di.NaverRetrofitModule.NaverType
import com.sopt.peekabookaos.di.RefreshRetrofitModule.RefreshType
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
    fun providesRefreshService(@RefreshType retrofit: Retrofit): RefreshService =
        retrofit.create(RefreshService::class.java)

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

    @Provides
    fun providesUserInputService(@PeekaType retrofit: Retrofit): UserInputService =
        retrofit.create(UserInputService::class.java)

    @Provides
    fun providesMyPageService(@PeekaType retrofit: Retrofit): MyPageService =
        retrofit.create(MyPageService::class.java)

    @Provides
    fun providesReportService(@PeekaType retrofit: Retrofit): ReportService =
        retrofit.create(ReportService::class.java)

    @Provides
    fun providesForceUpdateService(@PeekaType retrofit: Retrofit): ForcedUpdateService =
        retrofit.create(ForcedUpdateService::class.java)
}
