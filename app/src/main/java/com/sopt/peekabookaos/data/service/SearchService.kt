package com.sopt.peekabookaos.data.service

import com.sopt.peekabookaos.data.entity.BaseResponse
import com.sopt.peekabookaos.data.entity.User
import retrofit2.http.GET
import retrofit2.http.Path

interface SearchService {
    @GET("friend?nickname=")
    suspend fun getSearchUser(
        @Path("nickname") nickname: String
    ): BaseResponse<List<User>>
}
