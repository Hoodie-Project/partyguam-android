package com.party.domain.model.user.detail

import kotlinx.serialization.Serializable

@Serializable
data class LocationResponse(
    val id: Int,
    val province: String,
    val city: String,
)
