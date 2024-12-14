package com.party.domain.model.party

data class PartyModify(
    val id: Int,
    val partyTypeId: Int,
    val title: String,
    val content: String,
    val image: String? = null,
    //val status: String,
    //val createdAt: String,
    val updatedAt: String,
)
