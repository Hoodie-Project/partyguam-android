package com.party.data.dto.party

import kotlinx.serialization.Serializable

@Serializable
data class PartyDetailDto(
    val id: Int,
    val partyType: PartyTypeDto,
    val title: String,
    val content: String,
    val image: String ?= null,
    val status: String,
    val createdAt: String,
    val updatedAt: String,
)

@Serializable
data class PartyTypeDto(
    val id: Int,
    val type: String
)

@Serializable
data class MyInfoDto(
    val status: String,
    val createdAt: String,
    val updatedAt: String,
    val id: Int,
    val userId: Int,
    val partyId: Int,
    val positionId: Int,
    val authority: String,
)