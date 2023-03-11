package com.sopt.peekabookaos.domain.usecase

import com.sopt.peekabookaos.domain.repository.ShelfRepository
import javax.inject.Inject

class GetPickUseCase @Inject constructor(
    private val shelfRepository: ShelfRepository
) {
    suspend operator fun invoke() = shelfRepository.getPick()
}
