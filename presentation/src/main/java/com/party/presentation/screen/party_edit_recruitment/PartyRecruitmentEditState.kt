package com.party.presentation.screen.party_edit_recruitment

import com.party.common.component.partyRecruitmentEditTabList
import com.party.domain.model.party.PartyRecruitment

data class PartyRecruitmentEditState(
    val selectedTabText: String = partyRecruitmentEditTabList[0],

    val isShowHelpCard: Boolean = false,
    val isDesc: Boolean = false,

    val isLoadingPartyRecruitment: Boolean = false,
    val partyRecruitment: List<PartyRecruitment> = emptyList(),

    // 모집공고 마감 다이얼로그
    val isShowPartyRecruitmentCompletedDialog: Boolean = false,

    // 모집공고 삭제 다이얼로그
    val isShowPartyRecruitmentDeleteDialog: Boolean = false,

    // 미리보기, 마감, 삭제 BottomSheet
    val isShowBottomSheet: Boolean = false,
    val selectedRecruitmentId: Int = -1,
    val selectedStatus: String = "",
    val description: String = "",
    val recruitingCount: Int = 0,
    val main: String = "",
    val sub: String = "",
)
