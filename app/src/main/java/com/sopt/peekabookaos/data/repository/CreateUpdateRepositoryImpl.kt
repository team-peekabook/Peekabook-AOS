package com.sopt.peekabookaos.data.repository

import com.sopt.peekabookaos.data.entity.BookComment
import com.sopt.peekabookaos.data.entity.NoResponse
import com.sopt.peekabookaos.data.entity.request.CreateBookRequest
import com.sopt.peekabookaos.data.entity.response.CreateBookResponse
import com.sopt.peekabookaos.data.source.remote.CreateUpdateDataSource
import javax.inject.Inject

class CreateUpdateRepositoryImpl @Inject constructor(
    private val createUpdateDataSource: CreateUpdateDataSource
) : CreateUpdateRepository {
    override suspend fun postCreateBook(createBookRequest: CreateBookRequest): Result<CreateBookResponse> =
        kotlin.runCatching { createUpdateDataSource.postCreateBook(createBookRequest) }
            .map { response ->
                response.data!!
            }

    override suspend fun patchBook(bookId: Int, bookComment: BookComment): Result<NoResponse> =
        kotlin.runCatching { createUpdateDataSource.patchBook(bookId, bookComment) }
}
