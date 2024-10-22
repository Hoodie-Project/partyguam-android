package com.party.data.entity.party

import kotlinx.serialization.Serializable

@Serializable
data class PartyListEntity(
    val parties: List<PartyItemEntity>,
    val total: Int,
)

@Serializable
data class PartyItemEntity(
    val id: Int,
    val partyType: PartyTypeItemEntity,
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
data class PartyTypeItemEntity(
    val id: Int,
    val type: String,
)
