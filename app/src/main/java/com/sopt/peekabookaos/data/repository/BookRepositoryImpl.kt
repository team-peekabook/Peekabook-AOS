package com.sopt.peekabookaos.data.repository

import com.sopt.peekabookaos.data.source.remote.BookDataSource
import com.sopt.peekabookaos.domain.repository.BookRepository
import javax.inject.Inject

class BookRepositoryImpl @Inject constructor(
    private val bookDataSource: BookDataSource
) : BookRepository {
    override suspend fun postCreateBook(
        bookImage: String,
        bookTitle: String,
        author: String,
        publisher: String,
        description: String?,
        memo: String?
    ): Result<Int> = kotlin.runCatching {
        bookDataSource.postCreateBook(
            bookImage,
            bookTitle,
            author,
            publisher,
            description,
            memo
        )
    }.map { response ->
        requireNotNull(response.data).bookId
    }

    override suspend fun patchEditBook(
        bookId: Int,
        description: String?,
        memo: String?
    ): Result<Unit> = kotlin.runCatching { bookDataSource.patchEditBook(bookId, description, memo) }

    override suspend fun postBookDuplicate(
        bookTitle: String,
        author: String,
        publisher: String
    ): Result<Boolean> =
        kotlin.runCatching { bookDataSource.postBookDuplicate(bookTitle, author, publisher) }
            .map { response ->
                requireNotNull(response.data).isDuplicate
            }
}
