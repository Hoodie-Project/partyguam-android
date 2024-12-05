package com.party.presentation.screen.state

sealed interface MyPartyAction {
    data class OnOrderByChange(val orderByDesc: Boolean): MyPartyAction
}