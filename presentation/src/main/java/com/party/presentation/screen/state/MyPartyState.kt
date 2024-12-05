package com.party.presentation.screen.state

import com.party.common.component.stateTabList
import com.party.domain.model.user.party.MyParty

data class MyPartyState(
    val selectedTabText: String = stateTabList[0],
    val isMyPartyLoading: Boolean = true,
    val myPartyList: MyParty = MyParty(total = 0, partyUsers = emptyList()),
    val orderByDesc: Boolean = true,

    val isMyRecruitmentLoading: Boolean = true,

)
