package com.sopt.peekabookaos.data.service

import com.sopt.peekabookaos.data.entity.BaseResponse
import com.sopt.peekabookaos.data.entity.NoResponse
import com.sopt.peekabookaos.data.entity.response.DetailResponse
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path

interface DetailService {
    @GET("bookshelf/detail/{bookId}")
    suspend fun getDetail(
        @Path("bookId") bookId: Int
    ): BaseResponse<DetailResponse>

    @DELETE("bookshelf/{bookId}")
    suspend fun deleteDetail(
        @Path("bookId") bookId: Int
    ): NoResponse
}
