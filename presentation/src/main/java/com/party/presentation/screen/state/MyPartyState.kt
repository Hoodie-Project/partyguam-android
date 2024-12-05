package com.party.presentation.screen.state

import com.party.domain.model.user.party.MyParty

data class MyPartyState(
    val isLoading: Boolean = true,
    val myPartyList: MyParty = MyParty(total = 0, partyUsers = emptyList()),
    val orderByDesc: Boolean = true
)
