package com.party.data.dto.party

import kotlinx.serialization.Serializable

@Serializable
data class ApprovalAndRejectionDto(
    val message: String,
)