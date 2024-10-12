package com.party.data.entity.user.detail

import kotlinx.serialization.Serializable

@Serializable
data class PersonalityListEntity(
    val id: Int,
    val content: String,
    val responseCount: String,
    val personalityOption: List<PersonalityListOptionEntity>
)

@Serializable
data class PersonalityListOptionEntity(
    val id: Int,
    val personalityQuestionId: Int,
    val content: String,
)
