package com.sopt.peekabookaos.domain.repository

import com.sopt.peekabookaos.data.entity.NoResponse
import com.sopt.peekabookaos.domain.entity.User

interface SearchRepository {
    suspend fun getSearchUser(nickname: String): Result<User>

    suspend fun postFollow(friendId: Int): Result<Unit>

    suspend fun deleteFollow(friendId: Int): Result<NoResponse>
}
