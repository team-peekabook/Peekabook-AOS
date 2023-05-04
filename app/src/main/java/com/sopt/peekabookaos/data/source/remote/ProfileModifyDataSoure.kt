package com.sopt.peekabookaos.data.source.remote

import com.sopt.peekabookaos.data.entity.NoResponse
import com.sopt.peekabookaos.data.service.ProfileModifyService
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

data class ProfileModifyDataSoure @Inject constructor(
    private val profileModifyService: ProfileModifyService
) {
    suspend fun patchProfileModify(
        file: MultipartBody.Part,
        requestBodyMap: HashMap<String, RequestBody>
    ): NoResponse = profileModifyService.patchProfileModify(
        file,
        requestBodyMap
    )
}
