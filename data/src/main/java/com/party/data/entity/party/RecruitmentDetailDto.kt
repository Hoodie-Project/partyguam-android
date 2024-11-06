package com.party.data.entity.party

import kotlinx.serialization.Serializable

@Serializable
data class RecruitmentDetailDto(
    val party: RecruitmentDetailPartyDto,
    val position: RecruitmentDetailPositionDto,
    val content: String,
    val recruitingCount: Int,
    val recruitedCount: Int,
    val applicationCount: Int,
    val createdAt: String,
    val isJoined: Boolean,
)

@Serializable
data class RecruitmentDetailPartyDto(
    val title: String,
    val image: String,
    val tag: String,
    val partyType: RecruitmentDetailPartyTypeDto,
)

@Serializable
data class RecruitmentDetailPartyTypeDto(
    val type: String,
)

@Serializable
data class RecruitmentDetailPositionDto(
    val main: String,
    val sub: String,
)