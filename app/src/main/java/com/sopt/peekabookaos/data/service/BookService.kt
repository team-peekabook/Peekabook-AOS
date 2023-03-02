package com.sopt.peekabookaos.data.service

import com.sopt.peekabookaos.data.entity.BaseResponse
import com.sopt.peekabookaos.data.entity.NoResponse
import com.sopt.peekabookaos.data.entity.request.CreateBookRequest
import com.sopt.peekabookaos.data.entity.request.EditBookRequest
import com.sopt.peekabookaos.data.entity.response.CreateBookResponse
import retrofit2.http.Body
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface BookService {
    @POST("bookshelf")
    suspend fun postCreateBook(
        @Body body: CreateBookRequest
    ): BaseResponse<CreateBookResponse>

    @PATCH("bookshelf/{bookshelfId}")
    suspend fun patchEditBook(
        @Path("bookshelfId") bookId: Int,
        @Body bookComment: EditBookRequest
    ): NoResponse
}
