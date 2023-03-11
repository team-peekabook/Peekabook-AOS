package com.sopt.peekabookaos.data.source.remote

import com.sopt.peekabookaos.data.entity.BaseResponse
import com.sopt.peekabookaos.data.entity.request.RecommendationRequest
import com.sopt.peekabookaos.data.entity.response.RecommendResponse
import com.sopt.peekabookaos.data.entity.response.RecommendationResponse
import com.sopt.peekabookaos.data.service.RecommendService
import javax.inject.Inject

data class RecommendDataSource @Inject constructor(
    private val recommendService: RecommendService
) {
    suspend fun getRecommend(): BaseResponse<RecommendResponse> =
        recommendService.getRecommend()

    suspend fun postRecommendation(
        recommendDesc: String?,
        bookTitle: String,
        bookImage: String,
        author: String,
        friendId: Int
    ): BaseResponse<RecommendationResponse> =
        recommendService.postRecommendation(
            RecommendationRequest(
                recommendDesc = recommendDesc,
                bookTitle = bookTitle,
                bookImage = bookImage,
                author = author
            ),
            friendId
        )
}
