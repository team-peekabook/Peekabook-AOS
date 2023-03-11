package com.sopt.peekabookaos.domain.usecase

import com.sopt.peekabookaos.domain.repository.NaverRepository
import javax.inject.Inject

class GetBookToBarcodeUseCase @Inject constructor(
    private val naverRepository: NaverRepository
) {
    suspend operator fun invoke(isbn: String) = naverRepository.getBookToBarcode(isbn)
}
