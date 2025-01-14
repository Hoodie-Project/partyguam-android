package com.party.presentation.screen.user_delete

sealed interface UserDeleteAction {
    data class OnShowDeleteDialog(val isShow: Boolean) : UserDeleteAction
    data object OnDelete: UserDeleteAction
}