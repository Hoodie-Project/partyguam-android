package com.party.presentation.screen.profile_edit_time

import com.party.domain.model.user.detail.PersonalityListOption

sealed interface ProfileEditTimeAction {
    data object OnReset: ProfileEditTimeAction
    data object OnApply: ProfileEditTimeAction
    data class OnSelectPersonality(val personalityListOption: PersonalityListOption): ProfileEditTimeAction
}