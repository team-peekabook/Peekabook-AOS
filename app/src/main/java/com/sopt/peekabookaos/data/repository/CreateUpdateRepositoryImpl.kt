package com.sopt.peekabookaos.data.repository

import com.sopt.peekabookaos.data.source.remote.CreateUpdateDataSource
import com.sopt.peekabookaos.domain.entity.Book
import com.sopt.peekabookaos.domain.repository.CreateUpdateRepository
import javax.inject.Inject

class CreateUpdateRepositoryImpl @Inject constructor(
    private val createUpdateDataSource: CreateUpdateDataSource
) : CreateUpdateRepository {
    override suspend fun postCreateBook(
        bookImage: String,
        bookTitle: String,
        author: String,
        description: String?,
        memo: String?
    ): Result<Book> =
        kotlin.runCatching {
            createUpdateDataSource.postCreateBook(
                bookImage,
                bookTitle,
                author,
                description,
                memo
            )
        }
            .map { response ->
                requireNotNull(response.data).toBook()
            }

    override suspend fun patchBook(
        bookId: Int,
        description: String?,
        memo: String?
    ): Result<Boolean> =
        kotlin.runCatching {
            createUpdateDataSource.patchBook(bookId, description, memo)
        }.map { response -> response.success }
}
