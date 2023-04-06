package com.sopt.peekabookaos.data.service

import com.sopt.peekabookaos.data.entity.BaseResponse
import com.sopt.peekabookaos.data.entity.NoResponse
import com.sopt.peekabookaos.data.entity.request.DuplicateRequest
import com.sopt.peekabookaos.data.entity.response.DuplicateResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface UserInputService {
    @POST("user/duplicate")
    suspend fun postDuplicate(@Body body: DuplicateRequest): BaseResponse<DuplicateResponse>

    @Multipart
    @PATCH("auth/signup")
    suspend fun patchSignUp(
        @Part profileImage: MultipartBody.Part,
        @PartMap body: HashMap<String, RequestBody>
    ): NoResponse
}
