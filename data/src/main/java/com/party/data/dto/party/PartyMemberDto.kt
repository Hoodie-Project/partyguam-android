package com.party.data.dto.party

import kotlinx.serialization.Serializable

@Serializable
data class PartyMembersInfoDto(
    val totalPartyUserCount: Int,
    val total: Int,
    val partyUser: List<PartyMemberInfoDto>,
)

@Serializable
data class PartyMemberInfoDto(
    val id: Int,
    val createdAt: String,
    //val updatedAt: String,
    val status: String,
    val authority: String,
    val user: PartyUserInfoDto,
    val position: PartyMemberPositionDto,
)

@Serializable
data class PartyUserInfoDto(
    //val id: Int,
    val nickname: String,
    val image: String? = null,
)

@Serializable
data class PartyMemberPositionDto(
    val main: String,
    val sub: String,
)