package com.sopt.peekabookaos.data.repository

import com.sopt.peekabookaos.data.source.remote.BookDataSource
import com.sopt.peekabookaos.domain.entity.Book
import com.sopt.peekabookaos.domain.repository.BookRepository
import javax.inject.Inject

class BookRepositoryImpl @Inject constructor(
    private val bookDataSource: BookDataSource
) : BookRepository {
    override suspend fun postCreateBook(
        bookImage: String,
        bookTitle: String,
        author: String,
        description: String?,
        memo: String?
    ): Result<Book> = kotlin.runCatching {
        bookDataSource.postCreateBook(
            bookImage,
            bookTitle,
            author,
            description,
            memo
        )
    }.map { response ->
        requireNotNull(response.data).toBook()
    }

    override suspend fun patchEditBook(
        bookId: Int,
        description: String?,
        memo: String?
    ): Result<Boolean> = kotlin.runCatching {
        bookDataSource.patchEditBook(bookId, description, memo)
    }.map { response -> response.success }
}
