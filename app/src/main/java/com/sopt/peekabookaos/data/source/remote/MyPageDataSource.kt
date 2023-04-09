package com.sopt.peekabookaos.data.source.remote

import com.sopt.peekabookaos.data.entity.BaseResponse
import com.sopt.peekabookaos.data.entity.SelfIntroEntity
import com.sopt.peekabookaos.data.service.MyPageService
import javax.inject.Inject

data class MyPageDataSource @Inject constructor(
    private val myPageService: MyPageService
) {
    suspend fun getMyPage(): BaseResponse<SelfIntroEntity> =
        myPageService.getMyPage()
}
