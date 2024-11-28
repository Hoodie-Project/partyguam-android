package com.party.data.entity.party

import kotlinx.serialization.Serializable

@Serializable
data class PartyApplyDto(
    val userId: Int,
    val partyRecruitmentId: Int,
    val message: String,
    val status: String,
    val createdAt: String,
    val updatedAt: String,
    val id: Int,
)
