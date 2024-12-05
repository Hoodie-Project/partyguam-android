package com.party.data.dto.party

import kotlinx.serialization.Serializable

@Serializable
data class RecruitmentListDto(
    val partyRecruitments: List<RecruitmentItemDto>,
    val total: Int,
)

@Serializable
data class RecruitmentItemDto(
    val id: Int,
    val recruitingCount: Int,
    val recruitedCount: Int,
    val content: String,
    val createdAt: String,
    val party: RecruitmentPartyDto,
    val position: RecruitmentPositionDto,
)

@Serializable
data class RecruitmentPartyDto(
    val id: Int,
    val title: String,
    val image: String?,
    val partyType: RecruitmentPartyTypeDto,
)

@Serializable
data class RecruitmentPartyTypeDto(
    val id: Int,
    val type: String,
)

@Serializable
data class RecruitmentPositionDto(
    val id: Int,
    val main: String,
    val sub: String,
)
