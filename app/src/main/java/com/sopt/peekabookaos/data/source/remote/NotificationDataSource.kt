package com.sopt.peekabookaos.data.source.remote

import com.sopt.peekabookaos.data.service.NotificationService
import javax.inject.Inject

data class NotificationDataSource @Inject constructor(
    private val notificationService: NotificationService
)
