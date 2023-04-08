package com.sopt.peekabookaos.data.service

import com.sopt.peekabookaos.data.entity.BaseResponse
import com.sopt.peekabookaos.data.entity.NoResponse
import com.sopt.peekabookaos.data.entity.PicksEntity
import com.sopt.peekabookaos.data.entity.request.PickRequest
import com.sopt.peekabookaos.data.entity.response.FriendShelfResponse
import com.sopt.peekabookaos.data.entity.response.MyShelfResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface ShelfService {
    @GET("bookshelf/friend/{friendId}")
    suspend fun getFriendShelf(@Path("friendId") friendId: Int): BaseResponse<FriendShelfResponse>

    @GET("bookshelf")
    suspend fun getMyShelf(): BaseResponse<MyShelfResponse>

    @GET("pick/all")
    suspend fun getPick(): BaseResponse<List<PicksEntity>>

    @PATCH("pick")
    suspend fun patchPick(@Body pickRequest: PickRequest): NoResponse

    @POST("friend/block/{friendId}")
    suspend fun postBlock(@Path("friendId") friendId: Int): NoResponse
}
