package com.party.presentation.screen.manage_applicant

import com.party.domain.model.party.PartyApplicationUser
import com.party.domain.model.party.PartyRecruitment

data class ManageApplicantState(
    val isShowRecruitmentList: Boolean = true,
    val isShowHelpIcon: Boolean = false,

    // 모집공고 리스트
    val isDesc: Boolean = true,
    val isLoadingRecruitment: Boolean = false,
    val partyRecruitment: List<PartyRecruitment> = emptyList(),

    // 지원자 현황
    val selectedRecruitmentStatus: String = "전체",
    val isShowApplicantCreatedDt: Boolean = true,

    // 지원자 리스트
    val isLoadingRecruitmentApplicant: Boolean = false,
    val recruitmentApplicantList: List<PartyApplicationUser> = emptyList(),

    // 선택된 모집공고
    val selectedRecruitmentId: Int = 0,
    val selectedRecruitmentMain: String = "",
    val selectedRecruitmentSub: String = "",
)
