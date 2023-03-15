package com.sopt.peekabookaos.domain.repository

import com.sopt.peekabookaos.domain.entity.Detail

interface DetailRepository {
    suspend fun getDetail(bookId: Int): Result<Detail>

    suspend fun deleteDetail(bookId: Int): Result<Boolean>
}
