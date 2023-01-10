package com.sopt.peekabookaos.data.repository

import com.sopt.peekabookaos.data.entity.response.MyShelfResponse

interface ShelfRepository {
    suspend fun getMyShelf(): Result<MyShelfResponse>
}
