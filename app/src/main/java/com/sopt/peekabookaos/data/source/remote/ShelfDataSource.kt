package com.sopt.peekabookaos.data.source.remote

import com.sopt.peekabookaos.data.entity.BaseResponse
import com.sopt.peekabookaos.data.entity.PickModify
import com.sopt.peekabookaos.data.entity.response.MyShelfResponse
import com.sopt.peekabookaos.data.service.ShelfService
import javax.inject.Inject

data class ShelfDataSource @Inject constructor(
    private val shelfService: ShelfService
) {
    suspend fun getPick(): BaseResponse<List<PickModify>> = shelfService.getPick()

    suspend fun getMyShelf(): BaseResponse<MyShelfResponse> = shelfService.getMyShelf()
}
