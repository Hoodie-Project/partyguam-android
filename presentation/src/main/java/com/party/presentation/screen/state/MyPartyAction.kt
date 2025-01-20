package com.party.presentation.screen.state

sealed interface MyPartyAction {
    data class OnSelectTab(val selectedTabText: String): MyPartyAction
    data class OnSelectStatus(val selectedStatus: String): MyPartyAction
    data class OnSelectRecruitmentTab(val selectedRecruitmentTabText: String): MyPartyAction
    data class OnOrderByChange(val orderByDesc: Boolean): MyPartyAction
    data class OnShowHelpCard(val isShowHelpCard: Boolean): MyPartyAction
    data class OnRecruitmentOrderByChange(val orderByRecruitmentDateDesc: Boolean): MyPartyAction
    data class OnExpandedFloating(val isExpandedFloating: Boolean): MyPartyAction
    data class OnCancelRecruitment(val partyId: Int, val partyApplicationId: Int): MyPartyAction
}