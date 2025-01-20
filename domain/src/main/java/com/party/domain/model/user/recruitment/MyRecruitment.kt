package com.party.domain.model.user.recruitment

data class MyRecruitment(
    val total: Int,
    val partyApplications: List<PartyApplication>
)

data class PartyApplication(
    val id: Int,
    val message: String,
    val status: String,
    val createdAt: String,
    val partyRecruitment: PartyRecruitment
)

data class PartyRecruitment(
    val id: Int,
    val status: String,
    val position: Position,
    val party: Party
)

data class Position(
    val main: String,
    val sub: String
)

data class Party(
    val id: Int,
    val title: String,
    val image: String?,
    val partyType: PartyType
)

data class PartyType(
    val type: String
)
