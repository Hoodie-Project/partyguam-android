package com.party.domain.model.search

data class Search(
    val party: SearchedParty,
    val partyRecruitment: SearchedPartyRecruitment,
)

data class SearchedParty(
    val total: Int,
    val parties: List<SearchedPartyData>,
)

data class SearchedPartyData(
    val id: Int,
    val partyType: PartyType,
    val title: String,
    val content: String,
    val image: String? = null,
    val status: String,
    val createdAt: String,
    val updatedAt: String,
    val recruitmentCount: Int,
)

data class PartyType(
    val id: Int,
    val type: String,
)

data class SearchedPartyRecruitment(
    val total: Int,
    val partyRecruitments: List<SearchedRecruitmentData>
)

data class SearchedRecruitmentData(
    val id: Int,
    val content: String,
    val recruitingCount: Int,
    val recruitedCount: Int,
    val createdAt: String,
    val status: String,
    val party: Party,
    val position: Position,
)

data class Party(
    val id: Int,
    val title: String,
    val image: String? = null,
    val status: String,
    val partyType: PartyType,
)

data class Position(
    val id: Int,
    val main: String,
    val sub: String,
)