package com.sopt.peekabookaos.data.repository

import com.sopt.peekabookaos.data.source.remote.UserInputDataSource
import com.sopt.peekabookaos.domain.repository.UserInputRepository
import javax.inject.Inject

class UserInputRepositoryImpl @Inject constructor(
    private val userInputDataSource: UserInputDataSource
) : UserInputRepository {
    override suspend fun postDuplicate(
        nickname: String
    ): Result<Int> =
        kotlin.runCatching {
            userInputDataSource.postDuplicate(
                nickname
            )
        }.map { response -> requireNotNull(response.data).check }
}
