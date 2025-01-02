package com.party.presentation.screen.profile_edit_tendency

import com.party.domain.model.user.detail.PersonalityListOption

sealed interface ProfileEditTendencyAction {
    data class OnChangeSelectedTab(val selectedTab: String): ProfileEditTendencyAction
    data object OnApply: ProfileEditTendencyAction
    data class OnSelectTendency(val personalityListOption: PersonalityListOption): ProfileEditTendencyAction
    data class OnSelectConfidence(val personalityListOption: PersonalityListOption): ProfileEditTendencyAction
    data class OnSelectChallenge(val personalityListOption: PersonalityListOption): ProfileEditTendencyAction
    data object OnResetFirstArea: ProfileEditTendencyAction
    data object OnResetSecondArea: ProfileEditTendencyAction
    data object OnResetThirdArea: ProfileEditTendencyAction
}