package com.party.presentation.screen.party_edit

import com.party.domain.model.user.detail.PositionList

sealed interface PartyEditAction {
    data class OnIsVisibleToolTip(val isVisibleToolTip: Boolean): PartyEditAction

    data object OnRemovePartyTitle : PartyEditAction
    data class OnChangeInputTitle(val title: String): PartyEditAction

    data class OnChangePartyTypeSheet(val isPartyTypeSheetOpen: Boolean): PartyEditAction

    data class OnChangeIsShowHelpCard(val isShowHelpCard: Boolean): PartyEditAction
    data class OnChangePartyDescription(val description: String): PartyEditAction

    data class OnChangeMainPositionBottomSheet(val isMainPositionBottomSheetShow: Boolean): PartyEditAction
    data class OnChangeMainPosition(val position: String): PartyEditAction
    data class OnChangeSubPosition(val positionList: PositionList): PartyEditAction
}