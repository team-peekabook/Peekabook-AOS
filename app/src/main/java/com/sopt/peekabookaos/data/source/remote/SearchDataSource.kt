package com.sopt.peekabookaos.data.source.remote

import com.sopt.peekabookaos.data.entity.BaseResponse
import com.sopt.peekabookaos.data.entity.response.SearchUserResponse
import com.sopt.peekabookaos.data.service.SearchService
import javax.inject.Inject

data class SearchDataSource @Inject constructor(
    private val searchService: SearchService
) {
    suspend fun getSearchUser(nickname: String): BaseResponse<SearchUserResponse> =
        searchService.getSearchUser(nickname)
}
