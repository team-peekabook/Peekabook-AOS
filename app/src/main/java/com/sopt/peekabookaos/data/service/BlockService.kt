package com.sopt.peekabookaos.data.service

import com.sopt.peekabookaos.data.entity.NoResponse
import retrofit2.http.DELETE
import retrofit2.http.Path

interface BlockService {
    @DELETE("mypage/blocklist/{friendId}")
    suspend fun deleteBlock(
        @Path("friendId") friendId: Int
    ): NoResponse
}
