package com.sopt.peekabookaos.data.service

import com.sopt.peekabookaos.data.entity.BaseResponse
import com.sopt.peekabookaos.data.entity.request.DuplicateRequest
import com.sopt.peekabookaos.data.entity.response.DuplicateResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface UserInputService {
    @POST("user/duplicate")
    suspend fun postDuplicate(@Body body: DuplicateRequest): BaseResponse<DuplicateResponse>
}
