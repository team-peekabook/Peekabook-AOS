package com.sopt.peekabookaos.domain.repository

import com.sopt.peekabookaos.domain.entity.Version

interface ForceUpdateRepository {
    suspend fun getVersion(): Result<Version>
}