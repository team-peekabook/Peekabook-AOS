package com.sopt.peekabookaos.data.repository

import com.sopt.peekabookaos.data.entity.response.FriendShelfResponse

interface ShelfRepository {
    suspend fun getFriendShelf(friendId: Int): Result<FriendShelfResponse>
}
