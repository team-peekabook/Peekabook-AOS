package com.sopt.peekabookaos.data.repository

import com.sopt.peekabookaos.data.entity.NoResponse
import com.sopt.peekabookaos.data.entity.PickModify
import com.sopt.peekabookaos.data.entity.request.PickRequest
import com.sopt.peekabookaos.data.entity.response.FriendShelfResponse
import com.sopt.peekabookaos.data.entity.response.MyShelfResponse
import com.sopt.peekabookaos.data.source.remote.ShelfDataSource
import com.sopt.peekabookaos.domain.entity.MyShelf
import com.sopt.peekabookaos.domain.repository.ShelfRepository
import javax.inject.Inject

class ShelfRepositoryImpl @Inject constructor(
    private val shelfDataSource: ShelfDataSource
) : ShelfRepository {
    override suspend fun getFriendShelf(friendId: Int): Result<FriendShelfResponse> =
        kotlin.runCatching { shelfDataSource.getFriendShelf(friendId) }.map { response ->
            response.data!!
        }

    override suspend fun getMyShelf(): Result<MyShelf> =
        kotlin.runCatching { shelfDataSource.getMyShelf() }.map { response ->
            response.data!!.toMyShelf()
        }

    override suspend fun getPick(): Result<List<PickModify>> =
        kotlin.runCatching { shelfDataSource.getPick() }.map { response ->
            response.data!!
        }

    override suspend fun patchPick(pickRequest: PickRequest): Result<NoResponse> =
        kotlin.runCatching { shelfDataSource.patchPick(pickRequest) }.map { response -> response }
}
