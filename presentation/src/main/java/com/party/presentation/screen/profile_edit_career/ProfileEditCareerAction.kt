package com.party.presentation.screen.profile_edit_career

sealed interface ProfileEditCareerAction {
    data class OnChangePrevScreen(val isShowPrevScreen: Boolean): ProfileEditCareerAction
    data class OnChangeMainOrSub(val isMain: Boolean): ProfileEditCareerAction
    data class OnGetSubPositionList(val main: String): ProfileEditCareerAction
}