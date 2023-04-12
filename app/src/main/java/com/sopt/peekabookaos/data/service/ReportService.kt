package com.sopt.peekabookaos.data.service

import com.sopt.peekabookaos.data.entity.NoResponse
import com.sopt.peekabookaos.data.entity.request.ReportRequest
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface ReportService {
    @POST("/friend/{friendId}/report")
    suspend fun postReport(
        @Body body: ReportRequest,
        @Path("friendId") friendId: Int
    ): NoResponse
}
