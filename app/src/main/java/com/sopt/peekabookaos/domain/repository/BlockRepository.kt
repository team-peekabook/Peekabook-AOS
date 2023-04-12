package com.sopt.peekabookaos.domain.repository

import com.sopt.peekabookaos.domain.entity.User

interface BlockRepository {
    suspend fun getBlock(): Result<List<User>>

    suspend fun deleteBlock(friendId: Int): Result<Boolean>
}
