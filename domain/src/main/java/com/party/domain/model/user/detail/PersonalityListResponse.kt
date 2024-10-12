package com.party.domain.model.user.detail

import kotlinx.serialization.Serializable

@Serializable
data class PersonalityListResponse(
    val id: Int,
    val content: String,
    val responseCount: String,
    val personalityOption: List<PersonalityListOptionResponse>
)

@Serializable
data class PersonalityListOptionResponse(
    val id: Int,
    val personalityQuestionId: Int,
    val content: String,
)
