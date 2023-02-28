package com.sopt.peekabookaos.domain.usecase

import com.sopt.peekabookaos.domain.repository.DetailRepository
import javax.inject.Inject

class DeleteDetailUseCase @Inject constructor(
    private val detailRepository: DetailRepository
) {
    suspend operator fun invoke(bookId: Int) = detailRepository.deleteDetail(bookId)
}
