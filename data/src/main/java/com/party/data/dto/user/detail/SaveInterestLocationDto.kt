package com.party.data.dto.user.detail

import kotlinx.serialization.Serializable

@Serializable
data class SaveInterestLocationDto(
    val id: Int,
    val userId: Int,
    val locationId: Int,
)
