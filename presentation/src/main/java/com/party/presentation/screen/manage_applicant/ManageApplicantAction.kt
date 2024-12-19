package com.party.presentation.screen.manage_applicant

sealed interface ManageApplicantAction {
    data class OnShowHelpCard(val isShowHelpCard: Boolean): ManageApplicantAction
    data class OnChangeOrderBy(val isDesc: Boolean): ManageApplicantAction
    data class OnShowRecruitment(val isShow: Boolean): ManageApplicantAction
    data class OnSelectRecruitmentTab(val selectedRecruitmentTabText: String): ManageApplicantAction
    data class OnChangeApplicantOrderBy(val isDesc: Boolean): ManageApplicantAction
    data class OnSelectRecruitmentId(val partyRecruitmentId: Int): ManageApplicantAction
}