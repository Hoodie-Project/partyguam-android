package com.party.domain.model.user.detail

import kotlinx.serialization.Serializable

@Serializable
data class SaveInterestLocationResponse(
    val id: Int,
    val userId: Int,
    val locationId: Int,
)
