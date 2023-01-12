package com.sopt.peekabookaos.data.repository

import com.sopt.peekabookaos.data.entity.BookComment
import com.sopt.peekabookaos.data.entity.NoResponse
import com.sopt.peekabookaos.data.entity.request.CreateBookRequest
import com.sopt.peekabookaos.data.entity.response.CreateBookResponse

interface CreateUpdateRepository {
    suspend fun postCreateBook(createBookRequest: CreateBookRequest): Result<CreateBookResponse>

    suspend fun patchBook(bookId: Int, bookComment: BookComment): Result<NoResponse>
}
