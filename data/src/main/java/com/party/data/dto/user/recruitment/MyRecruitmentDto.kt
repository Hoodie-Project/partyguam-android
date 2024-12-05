package com.party.data.dto.user.recruitment

import kotlinx.serialization.Serializable

@Serializable
data class MyRecruitmentDto(
    val total: Int,
    val partyApplications: List<PartyApplicationDto>
)

@Serializable
data class PartyApplicationDto(
    val id: Int,
    val message: String,
    val status: String,
    val createdAt: String,
    val partyRecruitment: PartyRecruitmentDto
)

@Serializable
data class PartyRecruitmentDto(
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
    val partyType: PartyTypeDto
)

@Serializable
data class PartyTypeDto(
    val type: String
)