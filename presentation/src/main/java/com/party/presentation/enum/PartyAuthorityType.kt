package com.party.presentation.enum

import androidx.compose.ui.graphics.Color
import com.party.common.ui.theme.GRAY500
import com.party.common.ui.theme.PRIMARY

enum class PartyAuthorityType(
    val authority: String
) {
    MASTER(authority = "master"),
    DEPUTY(authority = "deputy"),
    MEMBER(authority = "member");

    companion object {
        fun fromAuthority(authority: String): PartyAuthorityType {
            return entries.find { it.authority == authority } ?: MEMBER
        }
    }
}

fun getPartyRole(authorityType: PartyAuthorityType): String {
    return when (authorityType) {
        PartyAuthorityType.MASTER -> "파티장"
        PartyAuthorityType.DEPUTY -> "부파티장"
        PartyAuthorityType.MEMBER -> "파티원"
    }
}

fun getPartyAuthorityColor(authorityType: PartyAuthorityType): Color {
    return when (authorityType) {
        PartyAuthorityType.MASTER -> PRIMARY
        PartyAuthorityType.DEPUTY -> GRAY500
        PartyAuthorityType.MEMBER -> GRAY500
    }
}