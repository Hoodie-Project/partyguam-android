package com.party.data.entity.user

import kotlinx.serialization.Serializable

@Serializable
data class SaveInterestLocationEntity(
    val id: Int,
    val userId: Int,
    val locationId: Int,
)
