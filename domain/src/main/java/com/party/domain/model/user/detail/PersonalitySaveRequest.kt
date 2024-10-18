package com.party.domain.model.user.detail

import kotlinx.serialization.Serializable

@Serializable
data class PersonalitySaveRequest(
    val personality: List<PersonalitySaveRequest2>
)

@Serializable
data class PersonalitySaveRequest2(
    val personalityQuestionId: Int,
    val personalityOptionId: List<Int>,
)
