package com.party.data.entity.party

import kotlinx.serialization.Serializable

@Serializable
data class PartyAuthorityDto(
    val userId: Int,
    val authority: String,
)
