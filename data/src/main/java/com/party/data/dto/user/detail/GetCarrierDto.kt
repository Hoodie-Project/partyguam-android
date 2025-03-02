package com.party.data.dto.user.detail

import kotlinx.serialization.Serializable

@Serializable
data class GetCarrierDto(
    val id: Int,
    val position: GetCarrierPositionDto,
    val years: Int,
    val careerType: String,
)

@Serializable
data class GetCarrierPositionDto(
    val id: Int,
    val main: String,
    val sub: String,
)