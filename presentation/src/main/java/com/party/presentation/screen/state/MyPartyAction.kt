package com.party.presentation.screen.state

sealed interface MyPartyAction {
    data class OnSelectTab(val selectedTabText: String): MyPartyAction
    data class OnOrderByChange(val orderByDesc: Boolean): MyPartyAction
}