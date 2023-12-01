package com.sopt.peekabookaos.domain.repository

import com.sopt.peekabookaos.domain.entity.ForcedUpdate

interface ForcedUpdateRepository {
    suspend fun hasForceUpdateVersion(
        currentVersion: String
    ): Result<ForcedUpdate>
}
