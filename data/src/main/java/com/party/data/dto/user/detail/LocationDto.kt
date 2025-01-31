package com.party.data.dto.user.detail

import kotlinx.serialization.Serializable

@Serializable
data class LocationDto(
    val id: Int,
    val province: String,
    val city: String,
)
