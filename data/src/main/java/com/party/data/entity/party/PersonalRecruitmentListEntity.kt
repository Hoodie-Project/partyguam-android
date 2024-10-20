package com.party.data.entity.party

import kotlinx.serialization.Serializable

@Serializable
data class PersonalRecruitmentListEntity(
    val parties: List<PersonalRecruitmentItemEntity>,
    val total: Int,
)

@Serializable
data class PersonalRecruitmentItemEntity(
    val partyRecruitmentId: Int,
    val main: String,
    val sub: String,
    val content: String,
    val recruitingCount: Int,
    val recruitedCount: Int,
    val applicationCount: Int,
    val createdAt: String,
    val party: PersonalRecruitmentPartyEntity,
    val position: PersonalRecruitmentPositionEntity,
)

@Serializable
data class PersonalRecruitmentPartyEntity(
    val title: String,
    val image: String,
)

@Serializable
data class PersonalRecruitmentPositionEntity(
    val id: Int,
    val main: String,
    val sub: String,
)
