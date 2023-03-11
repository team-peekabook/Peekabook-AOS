package com.sopt.peekabookaos.domain.usecase

import com.sopt.peekabookaos.data.entity.request.PickRequest
import com.sopt.peekabookaos.domain.repository.ShelfRepository
import javax.inject.Inject

class PatchPickUseCase @Inject constructor(
    private val shelfRepository: ShelfRepository
) {
    suspend operator fun invoke(pickRequest: PickRequest) = shelfRepository.patchPick(pickRequest)
}
