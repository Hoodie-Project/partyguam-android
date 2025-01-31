package com.party.data.dto.user

import kotlinx.serialization.Serializable

@Serializable
data class ReportsDto(
    val id: Int,
    val type: String,
    val typeId: Int,
    val content: String,
)
