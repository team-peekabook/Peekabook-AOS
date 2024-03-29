package com.sopt.peekabookaos.data.repository

import com.sopt.peekabookaos.data.source.remote.BlockDataSource
import com.sopt.peekabookaos.domain.entity.User
import com.sopt.peekabookaos.domain.repository.BlockRepository
import javax.inject.Inject

class BlockRepositoryImpl @Inject constructor(
    private val blockDataSource: BlockDataSource
) : BlockRepository {
    override suspend fun getBlock(): Result<List<User>> =
        kotlin.runCatching { blockDataSource.getBlock() }.map { response ->
            requireNotNull(response.data).map { blockEntity ->
                blockEntity.toFriendProfile()
            }
        }

    override suspend fun deleteBlock(friendId: Int): Result<Boolean> =
        kotlin.runCatching { blockDataSource.deleteBlock(friendId) }.map { response ->
            response.success
        }
}
