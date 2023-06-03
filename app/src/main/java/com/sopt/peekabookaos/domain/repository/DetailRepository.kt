package com.sopt.peekabookaos.domain.repository

import com.sopt.peekabookaos.domain.entity.BookDetail

interface DetailRepository {
    suspend fun getDetail(bookId: Int): Result<BookDetail>

    suspend fun deleteDetail(bookId: Int): Result<Boolean>
}
