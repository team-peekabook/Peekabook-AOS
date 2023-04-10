package com.sopt.peekabookaos.domain.repository

interface BlockRepository {
    suspend fun deleteBlock(friendId: Int): Result<Boolean>
}
