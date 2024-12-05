package com.party.data.dto.party

import kotlinx.serialization.Serializable

@Serializable
data class RecruitmentCreateDto(
    val id: Int,
    val content: String,
    val recruitingCount: Int,
    val recruitedCount: Int,
    val createdAt: String,
)
