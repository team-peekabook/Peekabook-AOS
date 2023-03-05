package com.sopt.peekabookaos.data.source.remote

import com.sopt.peekabookaos.data.entity.BaseResponse
import com.sopt.peekabookaos.data.entity.NoResponse
import com.sopt.peekabookaos.data.entity.request.BookCommentRequest
import com.sopt.peekabookaos.data.entity.request.CreateBookRequest
import com.sopt.peekabookaos.data.entity.response.CreateBookResponse
import com.sopt.peekabookaos.data.service.CreateUpdateService
import javax.inject.Inject

data class CreateUpdateDataSource @Inject constructor(
    private val createUpdateService: CreateUpdateService
) {
    suspend fun postCreateBook(
        bookImage: String,
        bookTitle: String,
        author: String,
        description: String?,
        memo: String?
    ): BaseResponse<CreateBookResponse> =
        createUpdateService.postCreateBook(
            CreateBookRequest(
                bookImage = bookImage,
                bookTitle = bookTitle,
                author = author,
                description = description,
                memo = memo
            )
        )

    suspend fun patchBook(bookId: Int, description: String?, memo: String?): NoResponse =
        createUpdateService.patchBook(bookId, BookCommentRequest(description, memo))
}
