package com.sopt.peekabookaos.domain.repository

import okhttp3.MultipartBody
import okhttp3.RequestBody

interface UserInputRepository {
    suspend fun postDuplicate(
        nickname: String
    ): Result<Int>

    suspend fun patchSignUp(
        file: MultipartBody.Part,
        requestBodyMap: HashMap<String, RequestBody>
    ): Result<Boolean>
}
