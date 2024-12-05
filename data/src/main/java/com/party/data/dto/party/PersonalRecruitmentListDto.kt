package com.party.data.dto.party

import kotlinx.serialization.Serializable

@Serializable
data class PersonalRecruitmentListDto(
    val partyRecruitments: List<PersonalRecruitmentItemDto>,
    val total: Int,
)

@Serializable
data class PersonalRecruitmentItemDto(
    val id: Int,
    val partyId: Int,
    val positionId: Int,
    val recruitingCount: Int,
    val recruitedCount: Int,
    val content: String,
    val createdAt: String,
    val party: PersonalRecruitmentPartyDto,
    val position: PersonalRecruitmentPositionDto,
)

@Serializable
data class PersonalRecruitmentPartyDto(
    val title: String,
    val image: String?,
    val partyType: PersonalRecruitmentPartyTypeDto,
)

@Serializable
data class PersonalRecruitmentPartyTypeDto(
    val id: Int,
    val type: String,
)

@Serializable
data class PersonalRecruitmentPositionDto(
    val id: Int,
    val main: String,
    val sub: String,
)
