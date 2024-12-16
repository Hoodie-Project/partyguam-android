package com.party.presentation.screen.party_edit_recruitment

sealed interface PartyRecruitmentEditAction {
    data class OnShowHelpCard(val isShowHelpCard: Boolean): PartyRecruitmentEditAction
    data class OnChangeOrderBy(val isDesc: Boolean): PartyRecruitmentEditAction
}