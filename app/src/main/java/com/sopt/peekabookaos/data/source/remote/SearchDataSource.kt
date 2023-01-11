package com.sopt.peekabookaos.data.source.remote

import com.sopt.peekabookaos.data.entity.BaseResponse
import com.sopt.peekabookaos.data.entity.NoResponse
import com.sopt.peekabookaos.data.entity.response.FollowResponse
import com.sopt.peekabookaos.data.entity.response.SearchUserResponse
import com.sopt.peekabookaos.data.service.SearchService
import javax.inject.Inject

data class SearchDataSource @Inject constructor(
    private val searchService: SearchService
) {
    suspend fun getSearchUser(nickname: String): BaseResponse<SearchUserResponse> =
        searchService.getSearchUser(nickname)

    suspend fun postFollow(friendId: Int): BaseResponse<FollowResponse> =
        searchService.postFollow(friendId)

    suspend fun deleteFollow(friendId: String): NoResponse =
        searchService.deleteFollow(friendId)
}
