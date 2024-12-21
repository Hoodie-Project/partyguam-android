package com.party.presentation.screen.home

sealed interface HomeAction {
    data class OnTabClick(val tabText: String) : HomeAction
    data object OnPersonalRecruitmentReload : HomeAction
}