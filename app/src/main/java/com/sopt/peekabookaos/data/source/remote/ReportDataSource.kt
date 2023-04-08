package com.sopt.peekabookaos.data.source.remote

import com.sopt.peekabookaos.data.entity.NoResponse
import com.sopt.peekabookaos.data.entity.request.ReportRequest
import com.sopt.peekabookaos.data.service.ReportService
import javax.inject.Inject

data class ReportDataSource @Inject constructor(
    private val reportService: ReportService
) {
    suspend fun postReport(
        reasonIndex: Int,
        etc: String?,
        friendId: Int
    ): NoResponse = reportService.postReport(
        ReportRequest(
            reasonIndex = reasonIndex,
            etc = etc
        ),
        friendId
    )
}
