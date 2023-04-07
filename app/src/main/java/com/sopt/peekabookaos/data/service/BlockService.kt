package com.sopt.peekabookaos.data.service

import com.sopt.peekabookaos.data.entity.BaseResponse
import com.sopt.peekabookaos.data.entity.FriendListEntity
import com.sopt.peekabookaos.data.entity.NoResponse
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path

interface BlockService {
    @GET("mypage/blocklist")
    suspend fun getBlock(): BaseResponse<List<FriendListEntity>>

    @DELETE("mypage/blocklist/{friendId}")
    suspend fun deleteBlock(
        @Path("friendId") friendId: Int
    ): NoResponse
}
