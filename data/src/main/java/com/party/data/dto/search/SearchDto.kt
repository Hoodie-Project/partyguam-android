package com.party.data.dto.search

import kotlinx.serialization.Serializable

@Serializable
data class SearchDto(
    val party: SearchedPartyDto,
    val partyRecruitment: SearchedPartyRecruitmentDto,
)

@Serializable
data class SearchedPartyDto(
    val total: Int,
    val parties: List<SearchedPartyDataDto>,
)

@Serializable
data class SearchedPartyDataDto(
    val id: Int,
    val partyType: PartyTypeDto,
    val title: String,
    val content: String,
    val image: String? = null,
    val status: String,
    val createdAt: String,
    val updatedAt: String,
    val recruitmentCount: Int,
)

@Serializable
data class PartyTypeDto(
    val id: Int,
    val type: String,
)


@Serializable
data class SearchedPartyRecruitmentDto(
    val total: Int,
    val partyRecruitments: List<SearchedRecruitmentDataDto>
)

@Serializable
data class SearchedRecruitmentDataDto(
    val id: Int,
    val content: String,
    val recruitingCount: Int,
    val recruitedCount: Int,
    val createdAt: String,
    val status: String,
    val party: PartyDto,
    val position: PositionDto,
)

@Serializable
data class PartyDto(
    val id: Int,
    val title: String,
    val image: String? = null,
    val status: String,
    val partyType: PartyTypeDto,
)

@Serializable
data class PositionDto(
    val id: Int,
    val main: String,
    val sub: String,
)