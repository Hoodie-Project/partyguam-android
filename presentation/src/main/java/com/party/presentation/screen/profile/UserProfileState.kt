package com.party.presentation.screen.profile

import com.party.domain.model.user.party.MyParty
import com.party.domain.model.user.profile.UserProfile
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody

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

    val image: MultipartBody.Part? = null,

    val isVisibleGender: Boolean = false,
    val isVisibleBirth: Boolean = false,

    val isMyPartyLoading: Boolean = true,
    val myPartyList: MyParty = MyParty(total = 0, partyUsers = emptyList()),
)
