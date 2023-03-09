package com.sopt.peekabookaos.domain.usecase

import com.sopt.peekabookaos.domain.repository.CreateUpdateRepository
import javax.inject.Inject

class PostCreateBookUseCase @Inject constructor(
    private val createUpdateRepository: CreateUpdateRepository
) {
    suspend operator fun invoke(
        bookImage: String,
        bookTitle: String,
        author: String,
        description: String?,
        memo: String?
    ) = createUpdateRepository.postCreateBook(bookImage, bookTitle, author, description, memo)
}
