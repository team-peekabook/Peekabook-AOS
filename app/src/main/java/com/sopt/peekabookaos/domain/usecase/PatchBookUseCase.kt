package com.sopt.peekabookaos.domain.usecase

import com.sopt.peekabookaos.domain.repository.CreateUpdateRepository
import javax.inject.Inject

class PatchBookUseCase @Inject constructor(
    private val createUpdateRepository: CreateUpdateRepository
) {
    suspend operator fun invoke(bookId: Int, description: String?, memo: String?) =
        createUpdateRepository.patchBook(bookId, description, memo)
}
