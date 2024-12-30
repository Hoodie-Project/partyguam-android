package com.party.presentation.screen.profile_edit

import okhttp3.MultipartBody

sealed interface ProfileEditAction{
    data class OnChangeImage(val image: MultipartBody.Part): ProfileEditAction
    data object OnChangeGenderVisible: ProfileEditAction
    data object OnChangeBirthVisible: ProfileEditAction
    data object OnModify: ProfileEditAction
}