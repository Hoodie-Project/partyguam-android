package com.party.domain.model.party

data class RecruitmentApplicant(
    val total: Int,
    val partyApplicationUser: List<PartyApplicationUser>,
)

data class PartyApplicationUser(
    val id: Int,
    val message: String,
    val status: String,
    val createdAt: String,
    val user: ApplicantUser
)

data class ApplicantUser(
    val id: Int,
    val nickname: String,
    val image: String?,
)
