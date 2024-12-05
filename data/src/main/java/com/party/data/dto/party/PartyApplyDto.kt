package com.party.data.dto.party

import kotlinx.serialization.Serializable

@Serializable
data class PartyApplyDto(
    val id: Int,
    val message: String,
    val status: String,
    val createdAt: String,
)
