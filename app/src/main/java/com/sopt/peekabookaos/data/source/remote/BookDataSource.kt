package com.sopt.peekabookaos.data.source.remote

import com.sopt.peekabookaos.data.entity.BaseResponse
import com.sopt.peekabookaos.data.entity.NoResponse
import com.sopt.peekabookaos.data.entity.request.BookDuplicateRequest
import com.sopt.peekabookaos.data.entity.request.CreateBookRequest
import com.sopt.peekabookaos.data.entity.request.EditBookRequest
import com.sopt.peekabookaos.data.entity.response.BookDuplicateResponse
import com.sopt.peekabookaos.data.entity.response.CreateBookResponse
import com.sopt.peekabookaos.data.service.BookService
import javax.inject.Inject

data class BookDataSource @Inject constructor(
    private val bookService: BookService
) {
    suspend fun postCreateBook(
        bookImage: String,
        bookTitle: String,
        author: String,
        publisher: String,
        description: String?,
        memo: String?
    ): BaseResponse<CreateBookResponse> = bookService.postCreateBook(
        CreateBookRequest(
            bookImage = bookImage,
            bookTitle = bookTitle,
            author = author,
            publisher = publisher,
            description = description,
            memo = memo
        )
    )

    suspend fun patchEditBook(bookId: Int, description: String?, memo: String?): NoResponse =
        bookService.patchEditBook(bookId, EditBookRequest(description, memo))

    suspend fun postBookDuplicate(
        bookTitle: String,
        author: String,
        publisher: String
    ): BaseResponse<BookDuplicateResponse> = bookService.postBookDuplicate(
        BookDuplicateRequest(bookTitle = bookTitle, author = author, publisher = publisher)
    )
}
