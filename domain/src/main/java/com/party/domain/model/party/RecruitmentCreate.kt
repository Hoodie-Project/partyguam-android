package com.party.domain.model.party

data class RecruitmentCreate(
    val id: Int,
    val partyTypeId: Int,
    val title: String,
    val content: String,
    val image: String,
    val status: String,
    val createdAt: String,
    val updatedAt: String,
)
