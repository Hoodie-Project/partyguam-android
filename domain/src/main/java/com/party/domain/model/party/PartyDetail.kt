package com.party.domain.model.party

data class PartyDetail(
    val id: Int,
    val partyType: PartyType,
    val tag: String,
    val title: String,
    val content: String,
    val image: String,
    val status: String,
    //val createdAt: String,
    //val updatedAt: String,
    //val status: String,
    val createdAt: String,
    val updatedAt: String,
    //val id: Int,
    //val userId: Int,
    //val partyId: Int,
    //val positionId: Int,
    //val authority: String,
)

data class PartyType(
    val id: Int,
    val type: String
)

/*
data class MyInfo(
    val status: String,
    val createdAt: String,
    val updatedAt: String,
    val id: Int,
    val userId: Int,
    val partyId: Int,
    val positionId: Int,
    val authority: String,
)*/
