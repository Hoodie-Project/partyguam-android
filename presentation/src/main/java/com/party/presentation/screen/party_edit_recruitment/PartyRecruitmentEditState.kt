package com.party.presentation.screen.party_edit_recruitment

import com.party.common.component.partyRecruitmentEditTabList
import com.party.domain.model.party.PartyRecruitment

data class PartyRecruitmentEditState(
    val selectedTabText: String = partyRecruitmentEditTabList[0],

    val isShowHelpCard: Boolean = false,
    val isDesc: Boolean = false,

    val isLoadingPartyRecruitment: Boolean = false,
    val partyRecruitment: List<PartyRecruitment> = emptyList(),

    val isShowRecruitmentDeleteDialog: Boolean = false,

    // 모집마감 다이얼로그
    val isShowPartyRecruitmentCompletedDialog: Boolean = false,
)
