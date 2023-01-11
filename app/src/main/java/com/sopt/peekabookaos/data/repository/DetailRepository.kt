package com.sopt.peekabookaos.data.repository

import com.sopt.peekabookaos.data.entity.NoResponse
import com.sopt.peekabookaos.data.entity.response.DetailResponse

interface DetailRepository {
    suspend fun getDetail(bookId: Int): Result<DetailResponse>

    suspend fun deleteDetail(bookId: Int): Result<NoResponse>
}
