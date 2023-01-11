package com.sopt.peekabookaos.data.repository

import com.sopt.peekabookaos.data.entity.response.DetailResponse
import com.sopt.peekabookaos.data.source.remote.DetailDataSource
import javax.inject.Inject

class DetailRepositoryImpl @Inject constructor(
    private val detailDataSource: DetailDataSource
) : DetailRepository {
    override suspend fun getDetail(): Result<DetailResponse> =
        kotlin.runCatching { detailDataSource.getDetail() }.map { response ->
            response.data!!
        }
}
