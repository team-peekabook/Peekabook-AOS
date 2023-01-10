package com.sopt.peekabookaos.data.repository

import com.sopt.peekabookaos.data.entity.NoResponse
import com.sopt.peekabookaos.data.entity.User
import com.sopt.peekabookaos.data.entity.response.FollowResponse
import com.sopt.peekabookaos.data.source.remote.SearchDataSource
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val searchDataSource: SearchDataSource
) : SearchRepository {
    override suspend fun getSearchUser(nickname: String): Result<User> =
        kotlin.runCatching { searchDataSource.getSearchUser(nickname) }.map { response ->
            response.data!!.toUser()
        }

    override suspend fun postFollow(friendId: String): Result<FollowResponse> =
        kotlin.runCatching { searchDataSource.postFollow(friendId) }.map { response ->
            response.data!!
        }

    override suspend fun deleteFollow(friendId: String): Result<NoResponse> =
        kotlin.runCatching { searchDataSource.deleteFollow(friendId) }
}
