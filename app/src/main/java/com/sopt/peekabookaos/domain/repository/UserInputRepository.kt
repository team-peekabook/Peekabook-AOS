package com.sopt.peekabookaos.domain.repository

interface UserInputRepository {
    suspend fun postDuplicate(
        nickname: String
    ): Result<Int>
}
