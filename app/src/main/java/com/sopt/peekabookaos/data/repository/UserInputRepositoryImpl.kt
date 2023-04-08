package com.sopt.peekabookaos.data.repository

import com.sopt.peekabookaos.data.source.remote.UserInputDataSource
import com.sopt.peekabookaos.domain.repository.UserInputRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody
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

    override suspend fun patchSignUp(
        file: MultipartBody.Part,
        requestBodyMap: HashMap<String, RequestBody>
    ): Result<Boolean> =
        kotlin.runCatching { userInputDataSource.patchSignUp(file, requestBodyMap) }
            .map { response ->
                response.success
            }
}
