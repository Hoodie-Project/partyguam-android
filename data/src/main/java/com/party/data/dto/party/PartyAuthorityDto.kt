package com.party.data.dto.party

import kotlinx.serialization.Serializable

@Serializable
data class PartyAuthorityDto(
    val id: Int,
    val authority: String,
    val position: PartyAuthorityPositionDto
)

@Serializable
data class PartyAuthorityPositionDto(
    val id: Int,
    val main: String,
    val sub: String,
)