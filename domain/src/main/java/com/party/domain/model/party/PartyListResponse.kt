package com.party.domain.model.party

import kotlinx.serialization.Serializable

@Serializable
data class PartyListResponse(
    val parties: List<PartyItemResponse>,
    val total: Int,
)

@Serializable
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
)

@Serializable
data class PartyTypeItemResponse(
    val id: Int,
    val type: String,
)
