package com.party.data.entity.party

import kotlinx.serialization.Serializable

@Serializable
data class PartyListDto(
    val parties: List<PartyItemDto>,
    val total: Int,
)

@Serializable
data class PartyItemDto(
    val id: Int,
    val partyType: PartyTypeItemDto,
    val tag: String,
    val title: String,
    val content: String,
    val image: String?,
    val status: String,
    val createdAt: String,
    val updatedAt: String,
    val recruitmentCount: Int,
)

@Serializable
data class PartyTypeItemDto(
    val id: Int,
    val type: String,
)