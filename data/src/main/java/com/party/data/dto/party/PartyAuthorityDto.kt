package com.party.data.dto.party

import kotlinx.serialization.Serializable

@Serializable
data class PartyAuthorityDto(
    val userId: Int,
    val authority: String,
)
