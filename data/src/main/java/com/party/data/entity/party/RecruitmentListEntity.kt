package com.party.data.entity.party

import kotlinx.serialization.Serializable

@Serializable
data class RecruitmentListEntity(
    val partyRecruitments: List<RecruitmentItemEntity>,
    val total: Int,
)

@Serializable
data class RecruitmentItemEntity(
    val id: Int,
    val partyId: Int,
    val positionId: Int,
    val recruitingCount: Int,
    val recruitedCount: Int,
    val content: String,
    val createdAt: String,
    val party: RecruitmentPartyEntity,
    val position: RecruitmentPositionEntity,
)

@Serializable
data class RecruitmentPartyEntity(
    val title: String,
    val image: String?,
    val partyType: RecruitmentPartyTypeEntity,
)

@Serializable
data class RecruitmentPartyTypeEntity(
    val id: Int,
    val type: String,
)

@Serializable
data class RecruitmentPositionEntity(
    val id: Int,
    val main: String,
    val sub: String,
)
