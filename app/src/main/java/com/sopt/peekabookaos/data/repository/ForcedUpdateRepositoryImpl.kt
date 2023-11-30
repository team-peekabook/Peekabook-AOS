package com.sopt.peekabookaos.data.repository

import com.sopt.peekabookaos.data.source.remote.ForcedUpdateDataSource
import com.sopt.peekabookaos.domain.entity.ForcedUpdate
import com.sopt.peekabookaos.domain.entity.UpdateInformation
import com.sopt.peekabookaos.domain.entity.Version
import com.sopt.peekabookaos.domain.repository.ForcedUpdateRepository
import javax.inject.Inject

class ForcedUpdateRepositoryImpl @Inject constructor(
    private val forcedUpdateDataSource: ForcedUpdateDataSource
) : ForcedUpdateRepository {
    override suspend fun hasForceUpdateVersion(
        currentVersion: String
    ): Result<ForcedUpdate> = kotlin.runCatching {
        forcedUpdateDataSource.getForcedUpdateVersion()
    }.map { response ->
        val (imageUrl, androidForceVersion, text) = requireNotNull(response.data)
        if (Version.of(androidForceVersion).greaterThan(Version.of(currentVersion))) {
            return@map ForcedUpdate.Need(UpdateInformation(imageUrl, text))
        } else {
            return@map ForcedUpdate.None
        }
    }
}
