package com.party.domain.model.party

data class PartyDetail(
    val id: Int,
    val partyType: PartyType,
    val title: String,
    val content: String,
    val image: String?= null,
    val status: String,
    val createdAt: String,
    val updatedAt: String,
)

data class PartyType(
    val id: Int,
    val type: String
)