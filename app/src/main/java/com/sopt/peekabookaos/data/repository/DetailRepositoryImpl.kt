package com.sopt.peekabookaos.data.repository

import com.sopt.peekabookaos.data.entity.NoResponse
import com.sopt.peekabookaos.data.entity.response.DetailResponse
import com.sopt.peekabookaos.data.source.remote.DetailDataSource
import javax.inject.Inject

class DetailRepositoryImpl @Inject constructor(
    private val detailDataSource: DetailDataSource
) : DetailRepository {
    override suspend fun getDetail(bookId: Int): Result<DetailResponse> =
        kotlin.runCatching { detailDataSource.getDetail(bookId) }.map { response ->
            response.data!!
        }

    override suspend fun deleteDetail(bookId: Int): Result<NoResponse> =
        kotlin.runCatching { detailDataSource.deleteDetail(bookId) }
}
