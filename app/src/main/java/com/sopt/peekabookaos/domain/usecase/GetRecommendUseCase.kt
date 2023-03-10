package com.sopt.peekabookaos.domain.usecase

import com.sopt.peekabookaos.domain.repository.RecommendRepository
import javax.inject.Inject

class GetRecommendUseCase @Inject constructor(
    private val recommendRepository: RecommendRepository
) {
    suspend operator fun invoke() = recommendRepository.getRecommend()
}
