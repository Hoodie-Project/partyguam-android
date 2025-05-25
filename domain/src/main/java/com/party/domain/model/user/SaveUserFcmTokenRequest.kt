package com.party.domain.model.user

import kotlinx.serialization.Serializable

@Serializable
data class SaveUserFcmTokenRequest(
    val fcmToken: String,
    val device: String = "android"
)
