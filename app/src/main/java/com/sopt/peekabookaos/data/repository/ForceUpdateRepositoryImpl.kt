package com.sopt.peekabookaos.data.repository

import com.sopt.peekabookaos.data.source.remote.ForceUpdateDataSource
import com.sopt.peekabookaos.domain.entity.Version
import com.sopt.peekabookaos.domain.repository.ForceUpdateRepository
import javax.inject.Inject

class ForceUpdateRepositoryImpl @Inject constructor(
    private val forceUpdateDataSource: ForceUpdateDataSource
) : ForceUpdateRepository {
    override suspend fun getVersion(): Result<Version> =
        kotlin.runCatching { forceUpdateDataSource.getVersion() }.map { response ->
            requireNotNull(response.data).toVersion()
        }
}