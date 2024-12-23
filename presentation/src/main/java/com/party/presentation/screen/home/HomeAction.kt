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

    // 직무
    data class OnPositionSheetOpen(val isVisibleModal: Boolean) : HomeAction
    data class OnDescRecruitment(val isDesc: Boolean) : HomeAction
    data class OnMainPositionClick(val mainPosition: String): HomeAction
    data class OnSubPositionClick(val subPosition: String): HomeAction
    data class OnDelete(val position: Pair<String, String>): HomeAction
    data object OnPositionApply: HomeAction
    data object OnPositionSheetReset: HomeAction

    // 모집공고 - 파티유형
    data class OnPartyTypeSheetOpenRecruitment(val isVisibleModal: Boolean) : HomeAction
    data class OnSelectedPartyTypeRecruitment(val partyType: String) : HomeAction
    data object OnSelectedPartyTypeResetRecruitmentReset : HomeAction
    data object OnPartyTypeApplyRecruitment : HomeAction
}