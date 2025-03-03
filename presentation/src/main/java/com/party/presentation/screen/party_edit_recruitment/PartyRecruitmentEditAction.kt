package com.party.presentation.screen.party_edit_recruitment

sealed interface PartyRecruitmentEditAction {
    data class OnShowHelpCard(val isShowHelpCard: Boolean): PartyRecruitmentEditAction
    data class OnChangeOrderBy(val isDesc: Boolean): PartyRecruitmentEditAction
    data class OnExpanded(val index: Int, val isOptionsRevealed: Boolean): PartyRecruitmentEditAction
    data class OnCollapsed(val index: Int, val isOptionsRevealed: Boolean): PartyRecruitmentEditAction
    data class OnShowRecruitmentDeleteDialog(val isShowRecruitmentDeleteDialog: Boolean): PartyRecruitmentEditAction
    data class OnDeleteRecruitment(val partyId: Int, val partyRecruitmentId: Int): PartyRecruitmentEditAction
    data class OnShowPartyRecruitmentCompletedDialog(val isShowPartyRecruitmentDialog: Boolean): PartyRecruitmentEditAction
    data class OnPartyRecruitmentCompleted(val partyId: Int, val partyRecruitmentId: Int): PartyRecruitmentEditAction
}