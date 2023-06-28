package com.sopt.peekabookaos.data.service

import com.sopt.peekabookaos.data.entity.BaseResponse
import com.sopt.peekabookaos.data.entity.ProfileEntity
import com.sopt.peekabookaos.data.entity.response.ProfileModifyResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.Part
import retrofit2.http.PartMap

interface MyPageService {
    @GET("mypage/profile")
    suspend fun getMyPage(): BaseResponse<ProfileEntity>

    @Multipart
    @PATCH("mypage/profile")
    suspend fun patchProfileModify(
        @Part file: MultipartBody.Part?,
        @PartMap body: HashMap<String, RequestBody>
    ): BaseResponse<ProfileModifyResponse>
}
