package com.party.data.dto.party

import kotlinx.serialization.Serializable

@Serializable
data class RecruitmentApplicantDto(
    val total: Int,
    val partyApplicationUser: List<PartyApplicationUserDto>,
)

@Serializable
data class PartyApplicationUserDto(
    val id: Int,
    val message: String,
    val status: String,
    val createdAt: String,
    val user: ApplicantUserDto
)

@Serializable
data class ApplicantUserDto(
    val id: Int,
    val nickname: String,
    val image: String?,
)
