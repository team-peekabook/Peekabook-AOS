package com.sopt.peekabookaos.data.repository

import com.sopt.peekabookaos.data.entity.BaseResponse
import com.sopt.peekabookaos.data.entity.User

interface SearchRepository {
    suspend fun getSearchUser(nickname: String): Result<BaseResponse<List<User>>>
}
