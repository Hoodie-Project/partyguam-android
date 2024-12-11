package com.party.presentation.screen.party_create

import com.party.domain.model.user.detail.PositionList
import okhttp3.MultipartBody

sealed interface PartyCreateAction {
    data class OnIsShowDialogBack(val isBackShowDialog: Boolean): PartyCreateAction
    data class OnIsVisibleToolTip(val isVisibleToolTip: Boolean): PartyCreateAction
    data class OnChangeImage(val image: MultipartBody.Part): PartyCreateAction
    data class OnChangeInputTitle(val title: String): PartyCreateAction
    data object OnRemovePartyTitle : PartyCreateAction
    data class OnChangePartyTypeSheet(val isPartyTypeSheetOpen: Boolean): PartyCreateAction
    data class OnChangeSelectPartyType(val partyType: String): PartyCreateAction
    data class OnChangeIsShowHelpCard(val isShowHelpCard: Boolean): PartyCreateAction
    data class OnChangePartyDescription(val description: String): PartyCreateAction
    data class OnChangeMainPositionBottomSheet(val isMainPositionBottomSheetShow: Boolean): PartyCreateAction
    data class OnChangeMainPosition(val position: String): PartyCreateAction
    data class OnChangeSubPosition(val positionList: PositionList): PartyCreateAction
    data object OnPartyCreate: PartyCreateAction
}