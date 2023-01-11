package com.sopt.peekabookaos.data.repository

import com.sopt.peekabookaos.data.entity.PickModify
import com.sopt.peekabookaos.data.entity.response.MyShelfResponse

interface ShelfRepository {
    suspend fun getPick(): Result<List<PickModify>>
    suspend fun getMyShelf(): Result<MyShelfResponse>
}
