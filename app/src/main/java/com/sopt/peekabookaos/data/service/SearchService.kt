package com.sopt.peekabookaos.data.service

import com.sopt.peekabookaos.data.entity.BaseResponse
import com.sopt.peekabookaos.data.entity.response.SearchUserResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchService {
    @GET("friend?nickname=")
    suspend fun getSearchUser(
        @Query("nickname") nickname: String
    ): BaseResponse<List<SearchUserResponse>>
}
