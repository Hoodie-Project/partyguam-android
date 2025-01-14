package com.party.presentation.screen.auth_setting

sealed interface AuthSettingAction {
    data object OnLogout: AuthSettingAction
    data object OnUserDelete: AuthSettingAction
}