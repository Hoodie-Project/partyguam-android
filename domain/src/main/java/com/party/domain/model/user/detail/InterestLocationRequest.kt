package com.party.domain.model.user.detail

import kotlinx.serialization.Serializable

@Serializable
data class InterestLocationList(
    val locations: List<InterestLocationRequest>,
)

@Serializable
data class InterestLocationRequest(
    val id: Int,
)
