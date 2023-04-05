package com.sopt.peekabookaos.data.service

import com.sopt.peekabookaos.data.entity.BaseResponse
import com.sopt.peekabookaos.data.entity.FriendListEntity
import retrofit2.http.GET

interface BlockService {
    @GET("mypage/blocklist")
    suspend fun getBlock(): BaseResponse<List<FriendListEntity>>
}
