package com.party.presentation.screen.manage_applicant

sealed interface ManageApplicantAction {
    data class OnShowHelpCard(val isShowHelpCard: Boolean): ManageApplicantAction
    data class OnChangeProgress(val isProgress: Boolean, val partyId: Int): ManageApplicantAction
    data class OnChangeOrderBy(val isDesc: Boolean): ManageApplicantAction
    data class OnShowRecruitment(val isShow: Boolean): ManageApplicantAction
    data class OnSelectRecruitmentTab(val selectedRecruitmentTabText: String): ManageApplicantAction
    data class OnChangeApplicantOrderBy(val isDesc: Boolean): ManageApplicantAction
    data class OnSelectRecruitmentId(val partyRecruitmentId: Int, val main: String, val sub: String): ManageApplicantAction

    // 수락하기
    data class OnAccept(val partyId: Int, val partyApplicationId: Int): ManageApplicantAction
    // 거절하기
    data class OnReject(val partyId: Int, val partyApplicationId: Int): ManageApplicantAction

    // 수락하기 Dialog
    data class OnShowAcceptDialog(val isShow: Boolean): ManageApplicantAction
    // 거절하기 Dialog
    data class OnShowRejectDialog(val isShow: Boolean): ManageApplicantAction
}