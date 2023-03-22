package com.sopt.peekabookaos.domain.usecase

import com.sopt.peekabookaos.domain.repository.RecommendRepository
import javax.inject.Inject

class PostRecommendationUseCase @Inject constructor(
    private val recommendRepository: RecommendRepository
) {
    suspend operator fun invoke(
        recommendDesc: String?,
        bookTitle: String,
        bookImage: String,
        author: String,
        publisher: String,
        friendId: Int
    ) = recommendRepository.postRecommendation(
        recommendDesc,
        bookTitle,
        bookImage,
        author,
        publisher,
        friendId
    )
}
