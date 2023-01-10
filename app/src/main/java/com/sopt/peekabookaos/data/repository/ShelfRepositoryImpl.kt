package com.sopt.peekabookaos.data.repository

import com.sopt.peekabookaos.data.entity.response.FriendShelfResponse
import com.sopt.peekabookaos.data.source.remote.ShelfDataSource
import javax.inject.Inject

class ShelfRepositoryImpl @Inject constructor(
    private val shelfDataSource: ShelfDataSource
) : ShelfRepository {
    override suspend fun getFriendShelf(friendId: Int): Result<FriendShelfResponse> =
        kotlin.runCatching { shelfDataSource.getFriendShelf(friendId) }.map { response ->
            response.data!!
        }
}
