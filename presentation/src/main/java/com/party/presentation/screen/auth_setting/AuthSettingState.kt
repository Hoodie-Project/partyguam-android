package com.party.presentation.screen.auth_setting

import com.party.domain.model.user.MySocialOauth

data class AuthSettingState(
    val isShowLogoutDialog: Boolean = false,

    val mySocialOauth: List<MySocialOauth> = emptyList()
)
