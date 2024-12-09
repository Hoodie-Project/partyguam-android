package com.party.data.dto.user.party

import kotlinx.serialization.Serializable

@Serializable
data class MyPartyDto(
    val total: Int,
    val partyUsers: List<PartyUserDto>
)

@Serializable
data class PartyUserDto(
    val id: Int,
    val createdAt: String,
    val position: PositionDto,
    val party: PartyDto
)

@Serializable
data class PositionDto(
    val main: String,
    val sub: String
)

@Serializable
data class PartyDto(
    val id: Int,
    val title: String,
    val image: String,
    val status: String,
    val partyType: PartyTypeDto
)

@Serializable
data class PartyTypeDto(
    val type: String
)