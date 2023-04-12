package com.sopt.peekabookaos.data.repository

import com.sopt.peekabookaos.data.source.remote.ReportDataSource
import com.sopt.peekabookaos.domain.repository.ReportRepository
import javax.inject.Inject

class ReportRepositoryImpl @Inject constructor(
    private val reportDataSource: ReportDataSource
) : ReportRepository {
    override suspend fun postReport(
        reasonIndex: Int,
        etc: String?,
        friendId: Int
    ): Result<Unit> =
        kotlin.runCatching {
            reportDataSource.postReport(
                reasonIndex,
                etc,
                friendId
            )
        }.map { response -> response.success }
}
