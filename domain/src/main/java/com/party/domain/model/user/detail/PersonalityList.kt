package com.party.domain.model.user.detail

data class PersonalityList(
    val id: Int,
    val content: String,
    val responseCount: String,
    val personalityOptions: List<PersonalityListOption>
)

data class PersonalityListOption(
    val id: Int,
    val personalityQuestionId: Int,
    val content: String,
)
