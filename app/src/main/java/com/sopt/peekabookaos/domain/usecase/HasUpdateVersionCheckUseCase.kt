package com.sopt.peekabookaos.domain.usecase

import com.sopt.peekabookaos.domain.repository.ForcedUpdateRepository
import javax.inject.Inject

class HasUpdateVersionCheckUseCase @Inject constructor(
    private val forcedUpdateRepository: ForcedUpdateRepository
) {
    suspend operator fun invoke(
        currentVersion: String
    ) = forcedUpdateRepository.hasForceUpdateVersion(currentVersion)
}
