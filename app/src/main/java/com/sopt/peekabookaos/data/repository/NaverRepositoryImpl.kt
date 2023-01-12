package com.sopt.peekabookaos.data.repository

import com.sopt.peekabookaos.data.entity.Book
import com.sopt.peekabookaos.data.source.remote.NaverDataSource
import javax.inject.Inject

class NaverRepositoryImpl @Inject constructor(
    private val naverDataSource: NaverDataSource
) : NaverRepository {
    override suspend fun getBookToBarcode(isbn: String): Result<Book> =
        kotlin.runCatching { naverDataSource.getBookToBarcode(isbn) }.map { response ->
            response.items[0].toBook()
        }
}
