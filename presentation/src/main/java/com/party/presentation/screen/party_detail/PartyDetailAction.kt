package com.party.presentation.screen.party_detail

sealed interface PartyDetailAction {
    data class OnTabClick(val tabText: String): PartyDetailAction
    data class OnShowPositionFilter(val isShow: Boolean): PartyDetailAction
    data class OnPositionClick(val position: String): PartyDetailAction
    data object OnReset: PartyDetailAction
    data class OnApply(val partyId: Int): PartyDetailAction
    data class OnChangeOrderBy(val selectedOrderBy: Boolean): PartyDetailAction
    data class OnChangeProgress(val isProgress: Boolean, val partyId: Int): PartyDetailAction

}