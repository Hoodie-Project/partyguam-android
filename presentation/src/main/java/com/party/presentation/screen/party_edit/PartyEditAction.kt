package com.party.presentation.screen.party_edit

import okhttp3.MultipartBody

sealed interface PartyEditAction {
    data class OnIsVisibleToolTip(val isVisibleToolTip: Boolean): PartyEditAction
    data class OnChangeImage(val image: MultipartBody.Part): PartyEditAction

    data object OnRemovePartyTitle : PartyEditAction
    data class OnChangeInputTitle(val title: String): PartyEditAction

    data class OnChangePartyTypeSheet(val isPartyTypeSheetOpen: Boolean): PartyEditAction
    data class OnChangeSelectPartyType(val partyType: String): PartyEditAction

    data class OnChangeIsShowHelpCard(val isShowHelpCard: Boolean): PartyEditAction
    data class OnChangePartyDescription(val description: String): PartyEditAction

    data class OnPartyModify(val partyId: Int): PartyEditAction

    data class OnChangeShowPartyDeleteDialog(val isShowPartyDeleteDialog: Boolean): PartyEditAction
}