package com.party.domain.model.user.profile

import kotlinx.serialization.Serializable

@Serializable
data class UserProfileModifyDto(
    val nickname: String,
    val birth: String,
    val birthVisible: Boolean,
    val gender: String,
    val genderVisible: Boolean,
    val portfolioTitle: String?,
    val portfolio: String?,
    val image: String?,
    val createdAt: String,
    val updatedAt: String
)
