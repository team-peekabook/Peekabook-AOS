package com.sopt.peekabookaos.domain.usecase

import com.sopt.peekabookaos.domain.repository.AuthRepository
import javax.inject.Inject

class GetFcmTokenUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke(setFcmToken: (String) -> Unit) =
        authRepository.getFcmToken(setFcmToken)
}
