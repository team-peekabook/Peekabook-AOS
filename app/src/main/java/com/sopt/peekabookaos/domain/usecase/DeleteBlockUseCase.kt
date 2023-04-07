package com.sopt.peekabookaos.domain.usecase

import com.sopt.peekabookaos.domain.repository.BlockRepository
import javax.inject.Inject

class DeleteBlockUseCase @Inject constructor(
    private val blockRepository: BlockRepository
) {
    suspend operator fun invoke(friendId: Int) = blockRepository.deleteBlock(friendId)
}
