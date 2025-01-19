package com.party.domain.model.party

data class PartyRecruitment(
    val id: Int,
    val position: Position1,
    val content: String,
    val status: String,
    val recruitingCount: Int,
    val recruitedCount: Int,
    val applicationCount: Int,
    val createdAt: String,
    val isOptionsRevealed: Boolean = false,
)

data class Position1(
    val main: String,
    val sub: String,
)
