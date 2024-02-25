package com.sopt.peekabookaos.domain.usecase

import com.sopt.peekabookaos.domain.repository.RecommendRepository
import javax.inject.Inject

class DeleteRecommendUseCase @Inject constructor(
    private val recommendRepository: RecommendRepository
) {
    suspend operator fun invoke(recommendId: Int) = recommendRepository.deleteRecommend(recommendId)
}
