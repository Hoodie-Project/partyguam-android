package com.party.domain.model.user.party

data class MyParty(
    val total: Int,
    val partyUsers: List<PartyUser>
)

data class PartyUser(
    val id: Int,
    val createdAt: String,
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
    val status: String,
    val partyType: PartyType
)

data class PartyType(
    val type: String
)