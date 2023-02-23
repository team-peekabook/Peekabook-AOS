package com.sopt.peekabookaos.domain.usecase

import com.sopt.peekabookaos.domain.repository.SearchRepository
import javax.inject.Inject

class PostFollowUseCase @Inject constructor(
    private val searchRepository: SearchRepository
) {
    suspend operator fun invoke(friendId: Int) = searchRepository.postFollow(friendId)
}
