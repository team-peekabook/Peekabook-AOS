package com.sopt.peekabookaos.data.repository

import com.sopt.peekabookaos.data.entity.NoResponse
import com.sopt.peekabookaos.data.source.remote.SearchDataSource
import com.sopt.peekabookaos.domain.entity.User
import com.sopt.peekabookaos.domain.repository.SearchRepository
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val searchDataSource: SearchDataSource
) : SearchRepository {
    override suspend fun getSearchUser(nickname: String): Result<User> =
        kotlin.runCatching { searchDataSource.getSearchUser(nickname) }.map { response ->
            response.data!!.toUser()
        }

    override suspend fun postFollow(friendId: Int): Result<Unit> =
        kotlin.runCatching { searchDataSource.postFollow(friendId) }

    override suspend fun deleteFollow(friendId: Int): Result<NoResponse> =
        kotlin.runCatching { searchDataSource.deleteFollow(friendId) }
}
