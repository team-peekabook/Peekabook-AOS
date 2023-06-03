package com.sopt.peekabookaos.domain.repository

import com.sopt.peekabookaos.domain.entity.Picks
import com.sopt.peekabookaos.domain.entity.Shelf

interface ShelfRepository {
    suspend fun getMyShelf(): Result<Shelf>
    suspend fun patchPick(
        firstPick: Int?,
        secondPick: Int?,
        thirdPick: Int?
    ): Result<Boolean>

    suspend fun getFriendShelf(friendId: Int): Result<Shelf>
    suspend fun getPick(): Result<List<Picks>>
    suspend fun postBlock(friendId: Int): Result<Boolean>
}
