package com.party.domain.model.party

data class PartyListResponse(
    val parties: List<PartyItemResponse>,
    val total: Int,
)

data class PartyItemResponse(
    val id: Int,
    val partyType: PartyTypeItemResponse,
    val tag: String,
    val title: String,
    val content: String,
    val image: String?,
    val status: String,
    val createdAt: String,
    val updatedAt: String,
    val recruitmentCount: Int,
)

data class PartyTypeItemResponse(
    val id: Int,
    val type: String,
)