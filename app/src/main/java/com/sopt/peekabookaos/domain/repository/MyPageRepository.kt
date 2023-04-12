package com.sopt.peekabookaos.domain.repository

import com.sopt.peekabookaos.domain.entity.User

interface MyPageRepository {
    suspend fun getMyPage(): Result<User>
}
