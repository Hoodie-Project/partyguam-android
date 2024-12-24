package com.party.data.dto.user.profile

import kotlinx.serialization.Serializable

@Serializable
data class UserProfileDto(
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
    val userPersonalities: List<UserPersonalityDto>,
    val userCareers: List<UserCareerDto>,
    val userLocations: List<UserLocationDto>,
)

@Serializable
data class UserPersonalityDto(
    val id: Int,
    val personalityOption: PersonalityOptionDto,
)

@Serializable
data class PersonalityOptionDto(
    val id: Int,
    val content: String,
    val personalityQuestion: PersonalityQuestionDto,
)

@Serializable
data class PersonalityQuestionDto(
    val id: Int,
    val content: String,
    val responseCount: Int,
)

@Serializable
data class UserCareerDto(
    val id: Int,
    val years: Int,
    val careerType: String,
    val position: UserProfilePositionDto,
)

@Serializable
data class UserProfilePositionDto(
    val main: String,
    val sub: String,
)

@Serializable
data class UserLocationDto(
    val id: Int,
    val location: UserProfileLocationDto,
)

@Serializable
data class UserProfileLocationDto(
    val id: Int,
    val province: String,
    val city: String,
)