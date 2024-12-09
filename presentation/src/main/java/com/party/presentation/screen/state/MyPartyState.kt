package com.party.presentation.screen.state

import com.party.common.component.stateTabList
import com.party.domain.model.user.party.MyParty
import com.party.domain.model.user.recruitment.MyRecruitment

data class MyPartyState(
    val selectedTabText: String = stateTabList[0],

    val isMyPartyLoading: Boolean = true,
    val myPartyList: MyParty = MyParty(total = 0, partyUsers = emptyList()),
    val orderByDesc: Boolean = true,

    val isShowHelpCard: Boolean = false,
    val orderByRecruitmentDateDesc: Boolean = true,
    val isMyRecruitmentLoading: Boolean = true,
    val myRecruitmentList: MyRecruitment = MyRecruitment(total = 0, partyApplications = emptyList()),
)
