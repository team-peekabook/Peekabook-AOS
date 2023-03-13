package com.sopt.peekabookaos.domain.repository

import com.sopt.peekabookaos.domain.entity.FriendShelf
import com.sopt.peekabookaos.domain.entity.MyShelf
import com.sopt.peekabookaos.domain.entity.Picks

interface ShelfRepository {
    suspend fun getMyShelf(): Result<MyShelf>
    suspend fun patchPick(
        firstPick: Int?,
        secondPick: Int?,
        thirdPick: Int?
    ): Result<Boolean>
    suspend fun getFriendShelf(friendId: Int): Result<FriendShelf>
    suspend fun getPick(): Result<List<Picks>>
}
