package com.sopt.peekabookaos.domain.usecase

import com.sopt.peekabookaos.domain.repository.NaverRepository
import javax.inject.Inject

class GetBookToTitleUseCase @Inject constructor(
    private val naverRepository: NaverRepository
) {
    suspend operator fun invoke(title: String) = naverRepository.getBookToTitle(title)
}
