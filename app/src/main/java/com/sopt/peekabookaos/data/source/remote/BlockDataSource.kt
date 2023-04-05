package com.sopt.peekabookaos.data.source.remote

import com.sopt.peekabookaos.data.entity.BaseResponse
import com.sopt.peekabookaos.data.entity.FriendListEntity
import com.sopt.peekabookaos.data.service.BlockService
import javax.inject.Inject

class BlockDataSource @Inject constructor(
    private val blockService: BlockService
) {
    suspend fun getBlock(): BaseResponse<List<FriendListEntity>> =
        blockService.getBlock()
}
