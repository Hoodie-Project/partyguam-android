package com.party.domain.model.party

data class PartyList(
    val parties: List<PartyItem>,
    val total: Int,
)

data class PartyItem(
    val id: Int,
    val partyType: PartyTypeItem,
    val title: String,
    val content: String,
    val image: String? = null,
    val status: String,
    val createdAt: String,
    val updatedAt: String,
    val recruitmentCount: Int,
)

data class PartyTypeItem(
    val id: Int,
    val type: String,
)