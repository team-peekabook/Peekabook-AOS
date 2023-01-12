package com.sopt.peekabookaos.data.source.remote

import com.sopt.peekabookaos.data.entity.BaseResponse
import com.sopt.peekabookaos.data.entity.BookComment
import com.sopt.peekabookaos.data.entity.NoResponse
import com.sopt.peekabookaos.data.entity.request.CreateBookRequest
import com.sopt.peekabookaos.data.entity.response.CreateBookResponse
import com.sopt.peekabookaos.data.service.CreateUpdateService
import javax.inject.Inject

data class CreateUpdateDataSource @Inject constructor(
    private val createUpdateService: CreateUpdateService
) {
    suspend fun postCreateBook(createBookRequest: CreateBookRequest): BaseResponse<CreateBookResponse> =
        createUpdateService.postCreateBook(createBookRequest)

    suspend fun patchBook(bookId: Int, bookComment: BookComment): NoResponse =
        createUpdateService.patchBook(bookId, bookComment)
}
