package com.party.domain.model.user

data class MySocialOauth(
    val provider: String,
    val email: String,
    val image: String?
)