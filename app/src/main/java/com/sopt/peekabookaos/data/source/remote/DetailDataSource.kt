package com.sopt.peekabookaos.data.source.remote

import com.sopt.peekabookaos.data.entity.BaseResponse
import com.sopt.peekabookaos.data.entity.response.DetailResponse
import com.sopt.peekabookaos.data.entity.NoResponse
import com.sopt.peekabookaos.data.service.DetailService
import javax.inject.Inject

data class DetailDataSource @Inject constructor(
    private val detailService: DetailService
) {
    suspend fun getDetail(bookId: Int): BaseResponse<DetailResponse> =
        detailService.getDetail(bookId)

    suspend fun deleteDetail(bookId: Int): NoResponse =
        detailService.deleteDetail(bookId)
}
