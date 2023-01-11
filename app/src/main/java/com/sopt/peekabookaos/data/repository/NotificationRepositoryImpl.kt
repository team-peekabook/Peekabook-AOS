package com.sopt.peekabookaos.data.repository

import com.sopt.peekabookaos.data.entity.Notification
import com.sopt.peekabookaos.data.source.remote.NotificationDataSource
import javax.inject.Inject

class NotificationRepositoryImpl @Inject constructor(
    private val notificationDataSource: NotificationDataSource
) : NotificationRepository {
    override suspend fun getAlarm(): Result<List<Notification>> =
        kotlin.runCatching { notificationDataSource.getAlarm() }.map { response ->
            response.data!!
        }
}
