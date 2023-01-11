package com.sopt.peekabookaos.data.service

import com.sopt.peekabookaos.data.entity.BaseResponse
import com.sopt.peekabookaos.data.entity.NoResponse
import com.sopt.peekabookaos.data.entity.response.FollowResponse
import com.sopt.peekabookaos.data.entity.response.SearchUserResponse
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface SearchService {
    @GET("friend")
    suspend fun getSearchUser(
        @Query("nickname") nickname: String
    ): BaseResponse<SearchUserResponse>

    @POST("friend/{friendId}")
    suspend fun postFollow(
        @Path("friendId") friendId: Int
    ): BaseResponse<FollowResponse>

    @DELETE("friend/{friendId}")
    suspend fun deleteFollow(
        @Path("friendId") friendId: String
    ): NoResponse
}
