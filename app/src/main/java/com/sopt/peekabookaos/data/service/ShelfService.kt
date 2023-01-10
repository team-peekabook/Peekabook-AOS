package com.sopt.peekabookaos.data.service

import com.sopt.peekabookaos.data.entity.BaseResponse
import com.sopt.peekabookaos.data.entity.response.FriendShelfResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ShelfService {
    @GET("/bookshelf/friend/{friendId}")
    suspend fun getFriendShelf(@Path("friendId") friendId: Int): BaseResponse<FriendShelfResponse>
}
