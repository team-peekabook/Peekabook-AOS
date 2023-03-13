package com.sopt.peekabookaos.data.repository

import com.sopt.peekabookaos.data.source.remote.DetailDataSource
import com.sopt.peekabookaos.domain.entity.Detail
import com.sopt.peekabookaos.domain.repository.DetailRepository
import javax.inject.Inject

class DetailRepositoryImpl @Inject constructor(
    private val detailDataSource: DetailDataSource
) : DetailRepository {
    override suspend fun getDetail(bookId: Int): Result<Detail> =
        kotlin.runCatching { detailDataSource.getDetail(bookId) }.map { response ->
            requireNotNull(response.data).toDetail()
        }

    override suspend fun deleteDetail(bookId: Int): Result<Boolean> =
        kotlin.runCatching { detailDataSource.deleteDetail(bookId) }
            .map { response -> response.success }
}
