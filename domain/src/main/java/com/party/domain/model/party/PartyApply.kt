package com.party.domain.model.party

data class PartyApply(
    val userId: Int,
    val partyRecruitmentId: Int,
    val message: String,
    val status: String,
    val createdAt: String,
    val updatedAt: String,
    val id: Int,
)
