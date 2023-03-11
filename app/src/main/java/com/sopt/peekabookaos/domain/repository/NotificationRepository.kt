package com.sopt.peekabookaos.domain.repository

import com.sopt.peekabookaos.domain.entity.Notification

interface NotificationRepository {
    suspend fun getAlarm(): Result<List<Notification>>
}
