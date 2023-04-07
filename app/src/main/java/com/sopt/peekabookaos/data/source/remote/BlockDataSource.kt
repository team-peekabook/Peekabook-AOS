package com.sopt.peekabookaos.data.source.remote

import com.sopt.peekabookaos.data.entity.NoResponse
import com.sopt.peekabookaos.data.service.BlockService
import javax.inject.Inject

class BlockDataSource @Inject constructor(
    private val blockService: BlockService
) {
    suspend fun deleteBlock(friendId: Int): NoResponse =
        blockService.deleteBlock(friendId)
}
