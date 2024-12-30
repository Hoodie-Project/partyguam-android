package com.party.presentation.screen.profile_edit

sealed interface ProfileEditAction{
    data object OnChangeGenderVisible: ProfileEditAction
    data object OnChangeBirthVisible: ProfileEditAction
    data object OnModify: ProfileEditAction
}