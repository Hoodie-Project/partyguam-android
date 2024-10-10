package com.party.data.entity.user

import kotlinx.serialization.Serializable

@Serializable
data class LocationEntity(
    val id: Int,
    val province: String,
    val city: String,
)
