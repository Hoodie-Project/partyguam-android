package com.party.domain.model.user.profile

import kotlinx.serialization.Serializable

@Serializable
data class UserProfileRequest(
    val gender: String? = null,
    val genderVisible: Boolean? = null,
    val birth: String? = null,
    val birthVisible: Boolean? = null,
    val portfolioTitle: String? = null,
    val portfolio: String? = null,
)
