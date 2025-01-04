package com.party.domain.model.party

import kotlinx.serialization.Serializable

@Serializable
data class PersonalRecruitmentList(
    val partyRecruitments: List<PersonalRecruitmentItem>,
    val total: Int,
)

@Serializable
data class PersonalRecruitmentItem(
    val id: Int,
    //val partyId: Int,
    //val positionId: Int,
    val recruitingCount: Int,
    val recruitedCount: Int,
    val content: String,
    val createdAt: String,
    val party: PersonalRecruitmentParty,
    val position: PersonalRecruitmentPosition,
)

@Serializable
data class PersonalRecruitmentParty(
    val id: Int,
    val title: String,
    val image: String?,
    val partyType: PersonalRecruitmentPartyType,
)

@Serializable
data class PersonalRecruitmentPartyType(
    val id: Int,
    val type: String,
)

@Serializable
data class PersonalRecruitmentPosition(
    val id: Int,
    val main: String,
    val sub: String,
)