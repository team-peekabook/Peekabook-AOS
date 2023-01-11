package com.sopt.peekabookaos.data.source.remote

import com.sopt.peekabookaos.data.entity.BaseResponse
import com.sopt.peekabookaos.data.entity.response.DetailResponse
import com.sopt.peekabookaos.data.entity.response.RecommendResponse
import com.sopt.peekabookaos.data.service.DetailService
import javax.inject.Inject

data class DetailDataSource @Inject constructor(
    private val detailService: DetailService
) {
    suspend fun getDetail(): BaseResponse<DetailResponse> =
        detailService.getDetail()
}
