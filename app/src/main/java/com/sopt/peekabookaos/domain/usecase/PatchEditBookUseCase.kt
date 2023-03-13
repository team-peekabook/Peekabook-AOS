package com.sopt.peekabookaos.domain.usecase

import com.sopt.peekabookaos.domain.repository.BookRepository
import javax.inject.Inject

class PatchEditBookUseCase @Inject constructor(
    private val bookRepository: BookRepository
) {
    suspend operator fun invoke(bookId: Int, description: String?, memo: String?) =
        bookRepository.patchEditBook(bookId, description, memo)
}
