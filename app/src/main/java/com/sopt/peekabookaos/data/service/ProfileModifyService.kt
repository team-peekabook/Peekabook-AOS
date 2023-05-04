package com.sopt.peekabookaos.data.service

import com.sopt.peekabookaos.data.entity.NoResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.Part
import retrofit2.http.PartMap

interface ProfileModifyService {
    @Multipart
    @PATCH("mypage/profile")
    suspend fun patchProfileModify(
        @Part file: MultipartBody.Part,
        @PartMap body: HashMap<String, RequestBody>
    ): NoResponse
}
