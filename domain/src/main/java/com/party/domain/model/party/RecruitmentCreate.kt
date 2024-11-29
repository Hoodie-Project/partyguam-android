package com.party.domain.model.party

data class RecruitmentCreate(
    val id: Int,
    val content: String,
    val recruitingCount: Int,
    val recruitedCount: Int,
    val createdAt: String,
)
