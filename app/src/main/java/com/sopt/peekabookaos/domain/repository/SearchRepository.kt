package com.sopt.peekabookaos.domain.repository

import com.sopt.peekabookaos.domain.entity.User

interface SearchRepository {
    suspend fun getSearchUser(nickname: String): Result<User>

    suspend fun postFollow(friendId: Int): Result<Boolean>

    suspend fun deleteFollow(friendId: Int): Result<Boolean>
}
