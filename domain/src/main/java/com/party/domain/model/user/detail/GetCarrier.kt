package com.party.domain.model.user.detail

data class GetCarrier(
    val id: Int,
    val position: GetCarrierPosition,
    val years: Int,
    val careerType: String,
)

data class GetCarrierPosition(
    val id: Int,
    val main: String,
    val sub: String,
)