package com.sopt.peekabookaos.data.source.remote

import com.sopt.peekabookaos.data.entity.response.NaverBookResponse
import com.sopt.peekabookaos.data.service.NaverService
import javax.inject.Inject

class NaverDataSource @Inject constructor(
    private val naverService: NaverService
) {
    suspend fun getBookToTitle(title: String): NaverBookResponse =
        naverService.getBookToTitle(title)

    suspend fun getBookToBarcode(isbn: String): NaverBookResponse =
        naverService.getBookToBarcode(isbn)
}
