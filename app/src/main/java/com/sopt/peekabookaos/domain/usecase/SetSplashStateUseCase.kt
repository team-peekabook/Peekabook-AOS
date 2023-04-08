package com.sopt.peekabookaos.domain.usecase

import com.sopt.peekabookaos.domain.entity.SplashState
import com.sopt.peekabookaos.domain.repository.AuthRepository
import javax.inject.Inject

class SetSplashStateUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke(splashState: SplashState) {
        authRepository.setSplashState(splashState)
    }
}
