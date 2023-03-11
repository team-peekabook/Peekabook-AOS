package com.sopt.peekabookaos.data.repository

import com.sopt.peekabookaos.data.source.remote.SearchDataSource
import com.sopt.peekabookaos.domain.entity.User
import com.sopt.peekabookaos.domain.repository.SearchRepository
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val searchDataSource: SearchDataSource
) : SearchRepository {
    override suspend fun getSearchUser(nickname: String): Result<User> =
        kotlin.runCatching { searchDataSource.getSearchUser(nickname) }.map { response ->
            requireNotNull(response.data).toUser()
        }

    override suspend fun postFollow(friendId: Int): Result<Boolean> =
        kotlin.runCatching { searchDataSource.postFollow(friendId) }
            .map { response -> response.success }

    override suspend fun deleteFollow(friendId: Int): Result<Boolean> =
        kotlin.runCatching { searchDataSource.deleteFollow(friendId) }
            .map { response -> response.success }
}
