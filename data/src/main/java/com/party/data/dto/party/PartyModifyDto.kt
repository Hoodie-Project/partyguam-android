package com.party.data.dto.party

import kotlinx.serialization.Serializable

@Serializable
data class PartyModifyDto(
    val id: Int,
    val partyTypeId: Int,
    val title: String,
    val content: String,
    val image: String? = null,
)