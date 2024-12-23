package com.party.domain.model.party

data class RecruitmentDetail(
    val party: RecruitmentDetailParty,
    val position: RecruitmentDetailPosition,
    val content: String,
    val recruitingCount: Int,
    val recruitedCount: Int,
    val applicationCount: Int,
    val createdAt: String,
)

data class RecruitmentDetailParty(
    val title: String,
    val image: String,
    val status: String,
    val partyType: RecruitmentDetailPartyType,
)

data class RecruitmentDetailPartyType(
    val type: String,
)

data class RecruitmentDetailPosition(
    val id: Int,
    val main: String,
    val sub: String,
)