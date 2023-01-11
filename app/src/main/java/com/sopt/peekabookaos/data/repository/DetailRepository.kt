package com.sopt.peekabookaos.data.repository

import com.sopt.peekabookaos.data.entity.response.DetailResponse

interface DetailRepository {
    suspend fun getDetail(): Result<DetailResponse>
}
