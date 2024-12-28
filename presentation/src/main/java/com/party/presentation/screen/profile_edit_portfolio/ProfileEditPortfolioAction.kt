package com.party.presentation.screen.profile_edit_portfolio

sealed interface ProfileEditPortfolioAction {
    data class OnChangeLink(val link: String): ProfileEditPortfolioAction
    data class OnChangeTitle(val title: String): ProfileEditPortfolioAction
    data object OnReset: ProfileEditPortfolioAction
    data object OnApply: ProfileEditPortfolioAction
    data object OnDeleteLink: ProfileEditPortfolioAction
    data object OnDeleteTitle: ProfileEditPortfolioAction
}