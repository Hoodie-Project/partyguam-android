package com.party.presentation.screen.home

sealed interface HomeAction {
    data class OnTabClick(val tabText: String) : HomeAction
    data object OnPersonalRecruitmentReload : HomeAction

    data class OnPartyTypeSheetOpen(val isVisibleModal: Boolean) : HomeAction
    data class OnSelectedPartyType(val partyType: String) : HomeAction
    data object OnSelectedPartyTypeReset : HomeAction
    data object OnPartyTypeApply : HomeAction

    data class OnActivePartyToggle(val isActive: Boolean) : HomeAction
    data class OnDescPartyArea(val isDesc: Boolean) : HomeAction
}