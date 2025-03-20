package com.party.domain.model.user

import kotlinx.serialization.Serializable

@Serializable
data class RecoverInfo(
    val email: String,
    val deletedAt: String,
    val recoverAccessToken: String,
)
