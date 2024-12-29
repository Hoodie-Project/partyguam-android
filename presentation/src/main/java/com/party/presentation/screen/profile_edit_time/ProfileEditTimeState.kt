package com.party.presentation.screen.profile_edit_time

import com.party.domain.model.user.detail.PersonalityList
import com.party.domain.model.user.detail.PersonalityListOption

data class ProfileEditTimeState(
    val personalityList: List<PersonalityList> = emptyList(),

    val selectedList: List<PersonalityListOption> = emptyList()
)
