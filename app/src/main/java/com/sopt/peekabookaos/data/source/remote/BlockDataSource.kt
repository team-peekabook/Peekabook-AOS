package com.sopt.peekabookaos.data.source.remote

import com.sopt.peekabookaos.data.entity.BaseResponse
import com.sopt.peekabookaos.data.entity.BlockEntity
import com.sopt.peekabookaos.data.service.BlockService
import javax.inject.Inject

class BlockDataSource @Inject constructor(
    private val blockService: BlockService
) {
    suspend fun getBlock(): BaseResponse<List<BlockEntity>> =
        blockService.getBlock()
}
