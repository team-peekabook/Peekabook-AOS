package com.sopt.peekabookaos.domain.repository

import com.sopt.peekabookaos.domain.entity.FriendProfile

interface BlockRepository {
    suspend fun getBlock(): Result<List<FriendProfile>>

    suspend fun deleteBlock(friendId: Int): Result<Boolean>
}
