package com.party.data.entity.party

import kotlinx.serialization.Serializable

@Serializable
data class PersonalRecruitmentListEntity(
    val partyRecruitments: List<PersonalRecruitmentItemEntity>,
    val total: Int,
)

@Serializable
data class PersonalRecruitmentItemEntity(
    val id: Int,
    val partyId: Int,
    val positionId: Int,
    val recruitingCount: Int,
    val recruitedCount: Int,
    val content: String,
    val createdAt: String,
    val party: PersonalRecruitmentPartyEntity,
    val position: PersonalRecruitmentPositionEntity,
)

@Serializable
data class PersonalRecruitmentPartyEntity(
    val title: String,
    val image: String?,
    val partyType: PersonalRecruitmentPartyTypeEntity,
)

@Serializable
data class PersonalRecruitmentPartyTypeEntity(
    val id: Int,
    val type: String,
)

@Serializable
data class PersonalRecruitmentPositionEntity(
    val id: Int,
    val main: String,
    val sub: String,
)
