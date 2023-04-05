package com.sopt.peekabookaos.data.source.remote

import com.sopt.peekabookaos.data.entity.BaseResponse
import com.sopt.peekabookaos.data.entity.request.DuplicateRequest
import com.sopt.peekabookaos.data.entity.response.DuplicateResponse
import com.sopt.peekabookaos.data.service.UserInputService
import javax.inject.Inject

data class UserInputDataSource @Inject constructor(
    private val userInputService: UserInputService
) {
    suspend fun postDuplicate(
        nickname: String
    ): BaseResponse<DuplicateResponse> = userInputService.postDuplicate(
        DuplicateRequest(
            nickname = nickname
        )
    )
}
