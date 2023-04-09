package com.sopt.peekabookaos.domain.repository

import com.sopt.peekabookaos.domain.entity.SelfIntro

interface MyPageRepository {
    suspend fun getMyPage(): Result<SelfIntro>
}
