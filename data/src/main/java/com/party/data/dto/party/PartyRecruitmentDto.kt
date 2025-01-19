package com.party.data.dto.party

import kotlinx.serialization.Serializable

@Serializable
data class PartyRecruitmentDto(
    val id: Int,
    val position: Position,
    val content: String,
    val status: String,
    val recruitingCount: Int,
    val recruitedCount: Int,
    val applicationCount: Int,
    val createdAt: String,
)

@Serializable
data class Position(
    val main: String,
    val sub: String,
)
