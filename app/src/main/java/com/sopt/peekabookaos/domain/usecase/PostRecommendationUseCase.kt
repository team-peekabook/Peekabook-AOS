package com.sopt.peekabookaos.domain.usecase

import com.sopt.peekabookaos.data.entity.request.RecommendationRequest
import com.sopt.peekabookaos.domain.repository.RecommendRepository
import javax.inject.Inject

class PostRecommendationUseCase @Inject constructor(
    private val recommendRepository: RecommendRepository
) {
    suspend operator fun invoke(
        recommendationRequest: RecommendationRequest,
        friendId: Int
    ) = recommendRepository.postRecommendation(recommendationRequest, friendId)
}
