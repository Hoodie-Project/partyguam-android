package com.party.data.dto.user.notification

import kotlinx.serialization.Serializable

@Serializable
data class NotificationDto(
    val nextCursor: Int?,
    val notifications: List<NotificationDataDto>
)

@Serializable
data class NotificationDataDto(
    val id: Int,
    val notificationType: NotificationTypeDto,
    val title: String,
    val link: String?,
    val message: String,
    val image: String?,
    val isRead: Boolean,
    val createdAt: String,
)

@Serializable
data class NotificationTypeDto(
    val type: String,
    val label: String,
)