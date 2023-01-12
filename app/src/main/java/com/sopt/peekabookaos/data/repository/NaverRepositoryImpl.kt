package com.sopt.peekabookaos.data.repository

import com.sopt.peekabookaos.data.entity.Book
import com.sopt.peekabookaos.data.source.remote.NaverDataSource
import javax.inject.Inject

class NaverRepositoryImpl @Inject constructor(
    private val naverDataSource: NaverDataSource
) : NaverRepository {
    override suspend fun getBookToTitle(title: String): Result<List<Book>> =
        kotlin.runCatching { naverDataSource.getBookToTitle(title) }.map { response ->
            response.items.map { naverBookItem ->
                naverBookItem.toBook()
            }
        }

    override suspend fun getBookToBarcode(isbn: String): Result<List<Book>> =
        kotlin.runCatching { naverDataSource.getBookToBarcode(isbn) }.map { response ->
            response.items.map { naverBookItem ->
                naverBookItem.toBook()
            }
        }
}
