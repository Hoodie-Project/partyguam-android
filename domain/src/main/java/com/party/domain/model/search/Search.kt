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
    val tag: String,
    val title: String,
    val content: String,
    val image: String,
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
    val parties: List<SearchedRecruitmentData>
)

data class SearchedRecruitmentData(
    val partyRecruitmentId: Int,
    val main: String,
    val sub: String,
    val content: String,
    val recruitingCount: Int,
    val recruitedCount: Int,
    val applicationCount: Int,
    val createdAt: String,
    val party: Party,
    val position: Position,
)

data class Party(
    val title: String,
    val image: String,
    val partyType: PartyType,
)

data class Position(
    val id: Int,
    val main: String,
    val sub: String,
)