package com.sopt.peekabookaos.domain.usecase

import com.sopt.peekabookaos.domain.repository.AuthRepository
import javax.inject.Inject

class InitTokenUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke(accessToken: String, refreshToken: String, fcmToken: String) =
        authRepository.initToken(accessToken, refreshToken, fcmToken)
}
