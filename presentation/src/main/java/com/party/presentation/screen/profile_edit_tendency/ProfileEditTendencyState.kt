package com.party.presentation.screen.profile_edit_tendency

import com.party.common.component.profileEditTendencyTabList
import com.party.domain.model.user.detail.PersonalityList
import com.party.domain.model.user.detail.PersonalityListOption

data class ProfileEditTendencyState(
    val selectedTab: String = profileEditTendencyTabList[0],

    val personalityList: List<PersonalityList> = emptyList(),

    val selectedTendencyList: List<PersonalityListOption> = emptyList(),
    val selectedConfidenceList: List<PersonalityListOption> = emptyList(),
    val selectedChallengeList: List<PersonalityListOption> = emptyList()
)
