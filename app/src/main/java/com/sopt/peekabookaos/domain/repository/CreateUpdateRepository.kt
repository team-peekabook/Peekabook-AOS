package com.sopt.peekabookaos.domain.repository

import com.sopt.peekabookaos.domain.entity.Book

interface CreateUpdateRepository {
    suspend fun postCreateBook(
        bookImage: String,
        bookTitle: String,
        author: String,
        description: String?,
        memo: String?
    ): Result<Book>

    suspend fun patchBook(bookId: Int, description: String?, memo: String?): Result<Boolean>
}
