package com.party.presentation.enum

enum class PartyAuthorityType(
    val authority: String
) {
    MASTER(authority = "master"),
    DEPUTY(authority = "deputy"),
    MEMBER(authority = "member"),
}