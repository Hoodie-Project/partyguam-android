package com.party.presentation.screen.profile

import com.party.domain.model.user.party.MyParty
import com.party.domain.model.user.profile.UserProfile

data class UserProfileState(

    val isLoading: Boolean = false, // 프로필 로딩
    val userProfile: UserProfile = UserProfile(
        nickname = "",
        birth = "",
        birthVisible = false,
        gender = "",
        genderVisible = false,
        portfolioTitle = "",
        portfolio = "",
        image = "",
        createdAt = "",
        updatedAt = "",
        userPersonalities = emptyList(),
        userCareers = emptyList(),
        userLocations = emptyList()
    ),

    val isVisibleGender: Boolean = false,
    val isVisibleBirth: Boolean = false,

    val isMyPartyLoading: Boolean = true,
    val myPartyList: MyParty = MyParty(total = 0, partyUsers = emptyList()),
)
