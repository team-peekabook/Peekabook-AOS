package com.sopt.peekabookaos.data.repository

import com.sopt.peekabookaos.data.source.remote.ProfileModifyDataSoure
import com.sopt.peekabookaos.domain.repository.ProfileModifyRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class ProfileModifyRepositoryImpl @Inject constructor(
    private val profileModifyDataSoure: ProfileModifyDataSoure
) : ProfileModifyRepository {
    override suspend fun patchProfileModify(
        file: MultipartBody.Part,
        requestBodyMap: HashMap<String, RequestBody>
    ): Result<Boolean> =
        kotlin.runCatching { profileModifyDataSoure.patchProfileModify(file, requestBodyMap) }
            .map { response ->
                response.success
            }
}
