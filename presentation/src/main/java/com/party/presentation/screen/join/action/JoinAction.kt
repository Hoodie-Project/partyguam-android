package com.party.presentation.screen.join.action

sealed interface JoinAction {
    data class OnShowCancelJoinDialog(val isShow: Boolean): JoinAction
    data class OnInputNickName(val nickName: String): JoinAction
    data object OnResetNickName: JoinAction
    data object OnCheckNickName: JoinAction
    data class OnInputBirthDay(val birthDay: String): JoinAction
    data class OnSelectGender(val gender: String): JoinAction
    data object OnJoinComplete: JoinAction
}