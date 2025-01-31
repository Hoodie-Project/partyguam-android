package com.party.domain.model.party

data class CheckUserApplicationStatus(
    val id: Int,
    val message: String,
    val status: String,
    val createdAt: String,
)
