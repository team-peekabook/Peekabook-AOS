package com.sopt.peekabookaos.domain.usecase

import com.sopt.peekabookaos.domain.repository.ShelfRepository
import javax.inject.Inject

class PatchPickUseCase @Inject constructor(
    private val shelfRepository: ShelfRepository
) {
    suspend operator fun invoke(
        firstPick: Int?,
        secondPick: Int?,
        thirdPick: Int?
    ) = shelfRepository.patchPick(firstPick, secondPick, thirdPick)
}
