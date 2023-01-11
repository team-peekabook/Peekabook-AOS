package com.sopt.peekabookaos.data.repository

import com.sopt.peekabookaos.data.entity.NoResponse
import com.sopt.peekabookaos.data.entity.User
import com.sopt.peekabookaos.data.entity.response.FollowResponse

interface SearchRepository {
    suspend fun getSearchUser(nickname: String): Result<User>

    suspend fun postFollow(friendId: String): Result<FollowResponse>

    suspend fun deleteFollow(friendId: Int): Result<NoResponse>
}
