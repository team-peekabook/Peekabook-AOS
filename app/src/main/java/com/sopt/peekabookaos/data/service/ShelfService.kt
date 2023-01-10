package com.sopt.peekabookaos.data.service

import com.sopt.peekabookaos.data.entity.BaseResponse
import com.sopt.peekabookaos.data.entity.response.MyShelfResponse
import retrofit2.http.GET

interface ShelfService {
    @GET("bookshelf")
    suspend fun getMyShelf(): BaseResponse<MyShelfResponse>
}
