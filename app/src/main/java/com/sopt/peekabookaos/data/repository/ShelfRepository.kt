package com.sopt.peekabookaos.data.repository

import com.sopt.peekabookaos.data.entity.NoResponse
import com.sopt.peekabookaos.data.entity.PickModify
import com.sopt.peekabookaos.data.entity.request.PickRequest
import com.sopt.peekabookaos.data.entity.response.FriendShelfResponse
import com.sopt.peekabookaos.data.entity.response.MyShelfResponse

interface ShelfRepository {
    suspend fun getFriendShelf(friendId: Int): Result<FriendShelfResponse>
    suspend fun getPick(): Result<List<PickModify>>
    suspend fun getMyShelf(): Result<MyShelfResponse>
    suspend fun patchPick(pickRequest: PickRequest): Result<NoResponse>
}
