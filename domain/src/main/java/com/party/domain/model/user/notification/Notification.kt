package com.party.domain.model.user.notification

data class Notification(
    val nextCursor: Int,
    val notifications: List<NotificationData>
)


data class NotificationData(
    val id: Int,
    val notificationType: NotificationType,
    val title: String,
    val link: String,
    val message: String,
    val image: String?,
    val isRead: Boolean,
    val createdAt: String,
)

data class NotificationType(
    val type: String,
    val label: String,
)