package com.sopt.peekabookaos.data.repository

import com.sopt.peekabookaos.data.entity.Detail
import com.sopt.peekabookaos.data.source.remote.DetailDataSource
import javax.inject.Inject

class DetailRepositoryImpl @Inject constructor(
    private val detailDataSource: DetailDataSource
) : DetailRepository {
    override suspend fun getDetail(bookId: Int): Result<Detail> =
        kotlin.runCatching { detailDataSource.getDetail(bookId) }.map { response ->
            response.data!!
        }
}
