package com.sopt.peekabookaos.data.repository

import com.sopt.peekabookaos.data.entity.Book

interface NaverRepository {
    suspend fun getBookToBarcode(isbn: String): Result<Book>
}
