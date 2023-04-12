package com.sopt.peekabookaos.domain.usecase

import com.sopt.peekabookaos.domain.repository.ReportRepository
import javax.inject.Inject

class PostReportUseCase @Inject constructor(
    private val reportRepository: ReportRepository
) {
    suspend operator fun invoke(
        reasonIndex: Int,
        etc: String?,
        friendId: Int
    ) = reportRepository.postReport(reasonIndex, etc, friendId)
}
