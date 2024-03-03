package com.sopt.peekabookaos.domain.repository

interface BookRepository {
    suspend fun postCreateBook(
        bookImage: String,
        bookTitle: String,
        author: String,
        publisher: String,
        description: String?,
        memo: String?
    ): Result<Int>

    suspend fun patchEditBook(bookId: Int, description: String?, memo: String?): Result<Unit>

    suspend fun postBookDuplicate(
        bookTitle: String,
        author: String,
        publisher: String
    ): Result<Boolean>
}
