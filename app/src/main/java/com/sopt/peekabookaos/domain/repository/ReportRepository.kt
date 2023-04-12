package com.sopt.peekabookaos.domain.repository

interface ReportRepository {
    suspend fun postReport(
        reasonIndex: Int,
        etc: String?,
        friendId: Int
    ): Result<Unit>
}
