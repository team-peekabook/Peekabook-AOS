package com.sopt.peekabookaos.data.repository

import com.sopt.peekabookaos.data.entity.PickModify

interface ShelfRepository {
    suspend fun getPick(): Result<List<PickModify>>
}
