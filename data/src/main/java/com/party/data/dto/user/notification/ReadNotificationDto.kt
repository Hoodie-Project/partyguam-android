package com.party.data.dto.user.notification

import kotlinx.serialization.Serializable

@Serializable
data class ReadNotificationDto(
    val message: String,
)
