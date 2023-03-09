package com.sopt.peekabookaos.domain.repository

import com.sopt.peekabookaos.domain.entity.Book

interface NaverRepository {
    suspend fun getBookToTitle(title: String): Result<List<Book>>

    suspend fun getBookToBarcode(isbn: String): Result<List<Book>>
}
