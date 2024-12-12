package com.party.presentation.screen.state

import com.party.common.component.stateTabList
import com.party.domain.model.user.party.MyParty
import com.party.domain.model.user.recruitment.MyRecruitment

data class MyPartyState(
    val selectedTabText: String = stateTabList[0],

    // 내 파티
    val selectedStatus: String = "전체",
    val isMyPartyLoading: Boolean = true,
    val myPartyList: MyParty = MyParty(total = 0, partyUsers = emptyList()),
    val orderByDesc: Boolean = true,
    val isExpandedFloating: Boolean = false,
    var isMyPartyScroll: Boolean = false,

    // 내 지원목록
    val selectedRecruitmentStatus: String = "전체",
    val isShowHelpCard: Boolean = false,
    val orderByRecruitmentDateDesc: Boolean = true,
    val isMyRecruitmentLoading: Boolean = true,
    val myRecruitmentList: MyRecruitment = MyRecruitment(total = 0, partyApplications = emptyList()),
)
