package com.sopt.peekabookaos.domain.usecase

import com.sopt.peekabookaos.domain.repository.BookRepository
import javax.inject.Inject

class PostCreateBookUseCase @Inject constructor(
    private val bookRepository: BookRepository
) {
    suspend operator fun invoke(
        bookImage: String,
        bookTitle: String,
        author: String,
        description: String?,
        memo: String?
    ) = bookRepository.postCreateBook(bookImage, bookTitle, author, description, memo)
}
