package com.sopt.peekabookaos.data.repository

import com.sopt.peekabookaos.data.entity.User
import com.sopt.peekabookaos.data.source.remote.SearchDataSource
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val searchDataSource: SearchDataSource
) : SearchRepository {
    override suspend fun getSearchUser(nickname: String): Result<User> =
        kotlin.runCatching { searchDataSource.getSearchUser(nickname) }.map { response ->
            response.data!!.toUser()
        }
}
