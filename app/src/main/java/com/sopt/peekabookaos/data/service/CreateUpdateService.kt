package com.sopt.peekabookaos.data.service

import com.sopt.peekabookaos.data.entity.BaseResponse
import com.sopt.peekabookaos.data.entity.request.CreateBookRequest
import com.sopt.peekabookaos.data.entity.response.CreateBookResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface CreateUpdateService {
    @POST("bookshelf")
    suspend fun postCreateBook(
        @Body body: CreateBookRequest
    ): BaseResponse<CreateBookResponse>
}
