package com.sopt.peekabookaos.data.source.remote

import com.sopt.peekabookaos.data.entity.BaseResponse
import com.sopt.peekabookaos.data.entity.NoResponse
import com.sopt.peekabookaos.data.entity.PickModifyEntity
import com.sopt.peekabookaos.data.entity.request.PickRequest
import com.sopt.peekabookaos.data.entity.response.FriendShelfResponse
import com.sopt.peekabookaos.data.entity.response.MyShelfResponse
import com.sopt.peekabookaos.data.service.ShelfService
import javax.inject.Inject

data class ShelfDataSource @Inject constructor(
    private val shelfService: ShelfService
) {
    suspend fun getFriendShelf(friendId: Int): BaseResponse<FriendShelfResponse> =
        shelfService.getFriendShelf(friendId)

    suspend fun getPick(): BaseResponse<List<PickModifyEntity>> = shelfService.getPick()

    suspend fun getMyShelf(): BaseResponse<MyShelfResponse> = shelfService.getMyShelf()

    suspend fun patchPick(pickRequest: PickRequest): NoResponse =
        shelfService.patchPick(pickRequest)
}
