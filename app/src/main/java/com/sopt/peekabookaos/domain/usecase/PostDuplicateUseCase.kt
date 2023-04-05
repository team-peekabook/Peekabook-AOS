package com.sopt.peekabookaos.domain.usecase

import com.sopt.peekabookaos.domain.repository.UserInputRepository
import javax.inject.Inject

class PostDuplicateUseCase @Inject constructor(
    private val userInputRepository: UserInputRepository
) {
    suspend operator fun invoke(nickname: String) = userInputRepository.postDuplicate(nickname)
}
