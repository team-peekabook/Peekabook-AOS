package com.sopt.peekabookaos.data.source.remote

import com.sopt.peekabookaos.data.entity.BaseResponse
import com.sopt.peekabookaos.data.entity.response.FriendShelfResponse
import com.sopt.peekabookaos.data.service.ShelfService
import javax.inject.Inject

data class ShelfDataSource @Inject constructor(
    private val shelfService: ShelfService
) {
    suspend fun getFriendShelf(friendId: Int): BaseResponse<FriendShelfResponse> =
        shelfService.getFriendShelf(friendId)
}
