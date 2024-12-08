package com.party.presentation.screen.party_apply

sealed interface PartyApplyAction {
    data object OnShowDialog: PartyApplyAction
    data object OnDismissBackDialog: PartyApplyAction
    data class OnChangeInputText(val inputText: String): PartyApplyAction
    data object OnAllDeleteInputText: PartyApplyAction
    data class OnApply(val partyId: Int, val partyRecruitmentId: Int): PartyApplyAction
}