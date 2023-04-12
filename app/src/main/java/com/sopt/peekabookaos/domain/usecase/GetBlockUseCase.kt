package com.sopt.peekabookaos.domain.usecase

import com.sopt.peekabookaos.domain.repository.BlockRepository
import javax.inject.Inject

class GetBlockUseCase @Inject constructor(
    private val blockRepository: BlockRepository
) {
    suspend operator fun invoke() = blockRepository.getBlock()
}
