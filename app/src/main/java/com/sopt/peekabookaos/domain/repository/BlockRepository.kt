package com.sopt.peekabookaos.domain.repository

interface BlockRepository {
    suspend fun deleteUnblock(friendId: Int): Result<Boolean>
}
