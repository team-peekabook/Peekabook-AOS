package com.sopt.peekabookaos.data.service

import com.sopt.peekabookaos.data.entity.BaseResponse
import com.sopt.peekabookaos.data.entity.NoResponse
import com.sopt.peekabookaos.data.entity.request.CreateBookRequest
import com.sopt.peekabookaos.data.entity.response.CreateBookResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.POST
import retrofit2.http.Path

interface CreateUpdateService {
    @POST("bookshelf")
    suspend fun postCreateBook(
        @Body body: CreateBookRequest
    ): BaseResponse<CreateBookResponse>

    @DELETE("bookshelf/{bookId}")
    suspend fun deleteBook(
        @Path("bookId") bookId: Int
    ): NoResponse
}
