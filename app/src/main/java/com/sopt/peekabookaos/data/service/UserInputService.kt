package com.sopt.peekabookaos.data.service

import com.sopt.peekabookaos.data.entity.BaseResponse
import com.sopt.peekabookaos.data.entity.NoResponse
import com.sopt.peekabookaos.data.entity.request.DuplicateRequest
import com.sopt.peekabookaos.data.entity.response.DuplicateResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.PartMap

interface UserInputService {
    @POST("user/duplicate")
    suspend fun postDuplicate(@Body body: DuplicateRequest): BaseResponse<DuplicateResponse>

    @Multipart
    @PATCH("auth/signup")
    suspend fun patchSignUp(
        @Part file: MultipartBody.Part,
        @PartMap body: HashMap<String, RequestBody>
    ): NoResponse
}
