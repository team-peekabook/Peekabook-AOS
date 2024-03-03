package com.sopt.peekabookaos.domain.usecase

import com.sopt.peekabookaos.domain.repository.BookRepository
import javax.inject.Inject

class PostBookDuplicateUseCase @Inject constructor(
    private val bookRepository: BookRepository
) {
    suspend operator fun invoke(
        bookTitle: String,
        author: String,
        publisher: String,
    ) = bookRepository.postBookDuplicate(bookTitle, author, publisher)
}
