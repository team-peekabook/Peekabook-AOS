package com.sopt.peekabookaos.data.repository

import com.sopt.peekabookaos.data.entity.PickModify
import com.sopt.peekabookaos.data.source.remote.ShelfDataSource
import javax.inject.Inject

class ShelfRepositoryImpl @Inject constructor(
    private val shelfDataSource: ShelfDataSource
) : ShelfRepository {
    override suspend fun getPick(): Result<List<PickModify>> =
        kotlin.runCatching { shelfDataSource.getPick() }.map { response ->
            response.data!!
        }
}
