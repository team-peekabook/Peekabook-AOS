package com.sopt.peekabookaos.domain.repository

import com.sopt.peekabookaos.domain.entity.User
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface MyPageRepository {
    suspend fun getMyPage(): Result<User>

    suspend fun patchProfileModify(
        file: MultipartBody.Part?,
        requestBodyMap: HashMap<String, RequestBody>
    ): Result<Boolean>
}
