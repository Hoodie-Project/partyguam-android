package com.party.domain.model.user

data class Reports(
    val id: Int,
    val type: String,
    val typeId: Int,
    val content: String,
)
