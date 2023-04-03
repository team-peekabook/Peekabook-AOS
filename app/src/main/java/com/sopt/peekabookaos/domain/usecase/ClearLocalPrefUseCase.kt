package com.sopt.peekabookaos.domain.usecase

import com.sopt.peekabookaos.domain.repository.AuthRepository
import javax.inject.Inject

class ClearLocalPrefUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke() = authRepository.clearLocalPref()
}
