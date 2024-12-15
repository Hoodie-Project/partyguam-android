package com.party.presentation.screen.party_user_manage

import com.party.domain.model.user.detail.PositionList

sealed interface PartyUserAction {
    data class OnChangeInputText(val inputText: String): PartyUserAction
    data class OnChangePositionBottomSheet(val isOpenPositionBottomSheet: Boolean): PartyUserAction
    data class OnChangeMainPosition(val selectedMainPosition: String): PartyUserAction
    data class OnChangeOrderBy(val isDesc: Boolean): PartyUserAction
    data class OnManageBottomSheet(val isOpenManageBottomSheet: Boolean, val selectedMemberAuthority: String): PartyUserAction
    data class OnApply(val partyId: Int): PartyUserAction

    data class OnMainPositionClick(val mainPosition: String): PartyUserAction
    data class OnChangeModifyPositionSheet(val isOpenMainPositionSheet: Boolean): PartyUserAction
    data class OnSubPositionClick(val subPosition: String): PartyUserAction
    data class OnChangeSelectedSubPosition(val positionList: PositionList): PartyUserAction

    data class OnChangeModifyDialog(val isShowModifyDialog: Boolean): PartyUserAction
}