package com.party.data.entity.party

import kotlinx.serialization.Serializable

@Serializable
data class PartyDetailDto(
    val id: Int,
    val partyType: PartyTypeDto,
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
    //val myInfo: MyInfoDto,
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