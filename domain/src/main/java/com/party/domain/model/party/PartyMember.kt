package com.party.domain.model.party

data class PartyMembersInfo(
    val totalPartyUserCount: Int,
    val total: Int,
    val partyUser: List<PartyMemberInfo>,
)

data class PartyMemberInfo(
    val createdAt: String,
    val updatedAt: String,
    val status: String,
    val authority: String,
    val user: PartyUserInfo,
    val position: PartyMemberPosition,
)

data class PartyUserInfo(
    val id: Int,
    val nickname: String,
    val image: String? = null,
)

data class PartyMemberPosition(
    val main: String,
    val sub: String,
)
