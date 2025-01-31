package com.party.domain.model.user

import kotlinx.serialization.Serializable

@Serializable
data class ReportsRequest(
    val type: String,
    val typeId: Int,
    val content: String,
)
