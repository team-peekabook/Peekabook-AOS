package com.sopt.peekabookaos.data.repository

import com.sopt.peekabookaos.data.entity.Book

interface NaverRepository {
    suspend fun getBookToTitle(title: String): Result<List<Book>>

    suspend fun getBookToBarcode(isbn: String): Result<List<Book>>
}
