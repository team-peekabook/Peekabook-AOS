package com.sopt.peekabookaos.data.service

import com.sopt.peekabookaos.data.entity.BaseResponse
import com.sopt.peekabookaos.data.entity.NoResponse
import com.sopt.peekabookaos.data.entity.response.DetailResponse
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path

interface DetailService {
    @GET("bookshelf/detail/{bookshelfId}")
    suspend fun getDetail(
        @Path("bookshelfId") bookId: Int
    ): BaseResponse<DetailResponse>

    @DELETE("bookshelf/{bookshelfId}")
    suspend fun deleteDetail(
        @Path("bookshelfId") bookId: Int
    ): NoResponse
}
