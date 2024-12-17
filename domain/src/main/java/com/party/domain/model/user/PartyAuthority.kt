package com.party.domain.model.user

data class PartyAuthority(
    val id: Int,
    val authority: String,
    val position: PartyAuthorityPosition
)

data class PartyAuthorityPosition(
    val id: Int,
    val main: String,
    val sub: String,
)