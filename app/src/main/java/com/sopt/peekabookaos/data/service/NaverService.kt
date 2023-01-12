package com.sopt.peekabookaos.data.service

import com.sopt.peekabookaos.data.entity.response.NaverBookResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NaverService {
    suspend fun getBookToBarcode(
        @Query("d_isbn") isbn: String
    ): NaverBookResponse
}
