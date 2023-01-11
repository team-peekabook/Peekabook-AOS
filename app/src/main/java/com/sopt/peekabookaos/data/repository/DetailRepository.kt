package com.sopt.peekabookaos.data.repository

import com.sopt.peekabookaos.data.entity.Detail

interface DetailRepository {
    suspend fun getDetail(bookId: Int): Result<Detail>
}
