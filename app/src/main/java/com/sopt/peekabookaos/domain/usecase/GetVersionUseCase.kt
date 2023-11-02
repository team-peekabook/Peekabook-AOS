package com.sopt.peekabookaos.domain.usecase

import com.sopt.peekabookaos.domain.repository.ForceUpdateRepository
import javax.inject.Inject

class GetVersionUseCase @Inject constructor(
    private val forceUpdateRepository: ForceUpdateRepository
) {
    suspend operator fun invoke() = forceUpdateRepository.getVersion()
}
