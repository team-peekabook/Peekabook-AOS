package com.sopt.peekabookaos.data.repository

import com.sopt.peekabookaos.data.source.remote.NotificationDataSource
import javax.inject.Inject

class NotificationRepositoryImpl @Inject constructor(
    private val notificationDataSource: NotificationDataSource
) : NotificationRepository
