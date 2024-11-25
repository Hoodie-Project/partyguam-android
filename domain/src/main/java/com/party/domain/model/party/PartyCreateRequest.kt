package com.party.domain.model.party

import kotlinx.serialization.Serializable

@Serializable
data class PartyCreateRequest(
    val title: String,
    val content: String,
    val partyTypeId: Int,
    val positionId: Int,
)
