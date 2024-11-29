package com.party.data.entity.user.detail

import kotlinx.serialization.Serializable

@Serializable
data class PersonalityListDto(
    val id: Int,
    val content: String,
    val responseCount: String,
    val personalityOptions: List<PersonalityListOptionDto>
)

@Serializable
data class PersonalityListOptionDto(
    val id: Int,
    val personalityQuestionId: Int,
    val content: String,
)
