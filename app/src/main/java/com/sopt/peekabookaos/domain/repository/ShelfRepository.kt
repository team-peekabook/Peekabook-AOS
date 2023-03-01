package com.sopt.peekabookaos.domain.repository

import com.sopt.peekabookaos.data.entity.NoResponse
import com.sopt.peekabookaos.data.entity.request.PickRequest
import com.sopt.peekabookaos.domain.entity.MyShelf

interface ShelfRepository {
    suspend fun getMyShelf(): Result<MyShelf>
    suspend fun patchPick(pickRequest: PickRequest): Result<NoResponse>
}
