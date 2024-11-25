package com.party.data.entity.party

import kotlinx.serialization.Serializable

@Serializable
data class PartyCreateDto(
    val id: Int,
    val partyTypeId: Int,
    val title: String,
    val content: String,
    val image: String? = null,
    val status: String,
    val createdAt: String,
    val updatedAt: String,
)
