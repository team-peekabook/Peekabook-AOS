package com.sopt.peekabookaos.data.service

import com.sopt.peekabookaos.data.entity.BaseResponse
import com.sopt.peekabookaos.data.entity.Detail
import retrofit2.http.GET
import retrofit2.http.Path

interface DetailService {
    @GET("bookshelf/detail/{bookId}")
    suspend fun getDetail(
        @Path("bookId") bookId: Int
    ): BaseResponse<Detail>
}
