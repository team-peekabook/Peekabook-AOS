package com.sopt.peekabookaos.data.service

import com.sopt.peekabookaos.data.entity.BaseResponse
import com.sopt.peekabookaos.data.entity.Notification
import retrofit2.http.GET

interface NotificationService {
    @GET("/alarm")
    suspend fun getAlarm(): BaseResponse<List<Notification>>
}
