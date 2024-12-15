package com.party.presentation.screen.party_user_manage

sealed interface PartyUserAction {
    data class OnChangeInputText(val inputText: String): PartyUserAction
    data class OnChangePositionBottomSheet(val isOpenPositionBottomSheet: Boolean): PartyUserAction
    data class OnChangeMainPosition(val selectedMainPosition: String): PartyUserAction
    data class OnChangeOrderBy(val isDesc: Boolean): PartyUserAction
    data class OnManageBottomSheet(val isOpenManageBottomSheet: Boolean, val selectedMemberAuthority: String): PartyUserAction
    data class OnApply(val partyId: Int): PartyUserAction
}