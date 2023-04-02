package com.sopt.peekabookaos.domain.usecase

import com.sopt.peekabookaos.domain.repository.AuthRepository
import javax.inject.Inject

class PostLoginUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(socialPlatform: String) = authRepository.postLogin(socialPlatform)
}
