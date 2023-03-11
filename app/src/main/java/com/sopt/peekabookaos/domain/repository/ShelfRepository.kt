package com.sopt.peekabookaos.domain.repository

import com.sopt.peekabookaos.data.entity.NoResponse
import com.sopt.peekabookaos.data.entity.request.PickRequest
import com.sopt.peekabookaos.domain.entity.FriendShelf
import com.sopt.peekabookaos.domain.entity.MyShelf
import com.sopt.peekabookaos.domain.entity.Picks

interface ShelfRepository {
    suspend fun getMyShelf(): Result<MyShelf>
    suspend fun patchPick(pickRequest: PickRequest): Result<NoResponse>
    suspend fun getFriendShelf(friendId: Int): Result<FriendShelf>
    suspend fun getPick(): Result<List<Picks>>
}
