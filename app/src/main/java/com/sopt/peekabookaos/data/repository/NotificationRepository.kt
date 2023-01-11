package com.sopt.peekabookaos.data.repository

import com.sopt.peekabookaos.data.entity.Notification

interface NotificationRepository {
    suspend fun getAlarm(): Result<List<Notification>>
}
