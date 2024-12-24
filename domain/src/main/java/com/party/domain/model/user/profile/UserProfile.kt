package com.party.domain.model.user.profile

data class UserProfile(
    val nickname: String,
    val birth: String,
    val birthVisible: Boolean,
    val gender: String,
    val genderVisible: Boolean,
    val portfolioTitle: String?,
    val portfolio: String?,
    val image: String?,
    val createdAt: String,
    val updatedAt: String,
    val userPersonalities: List<UserPersonality>,
    val userCareers: List<UserCareer>,
    val userLocations: List<UserLocation>,
)

data class UserPersonality(
    val id: Int,
    val personalityOption: PersonalityOption,
)

data class PersonalityOption(
    val id: Int,
    val content: String,
    val personalityQuestion: PersonalityQuestion,
)

data class PersonalityQuestion(
    val id: Int,
    val content: String,
    val responseCount: Int,
)

data class UserCareer(
    val id: Int,
    val years: Int,
    val careerType: String,
    val position: UserProfilePosition,
)

data class UserProfilePosition(
    val main: String,
    val sub: String,
)

data class UserLocation(
    val id: Int,
    val location: UserProfileLocation,
)

data class UserProfileLocation(
    val id: Int,
    val province: String,
    val city: String,
)