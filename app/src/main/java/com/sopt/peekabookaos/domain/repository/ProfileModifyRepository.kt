package com.sopt.peekabookaos.domain.repository

import okhttp3.MultipartBody
import okhttp3.RequestBody

interface ProfileModifyRepository {
    suspend fun patchProfileModify(
        file: MultipartBody.Part,
        requestBodyMap: HashMap<String, RequestBody>
    ): Result<Boolean>
}
