package com.party.presentation.screen.search

import com.party.domain.model.party.PartyList
import com.party.domain.model.party.RecruitmentList
import com.party.domain.model.room.KeywordModel
import com.party.domain.model.search.Search
import com.party.domain.model.search.SearchedParty
import com.party.domain.model.search.SearchedPartyRecruitment

data class SearchState(
    val isShowKeywordArea: Boolean = true,
    val keywordList: List<KeywordModel> = emptyList(),
    val selectedTabText: String = "전체",
    val inputKeyword: String = "",

    val isLoadingAllSearch: Boolean = true,
    val allSearchedList: Search = Search(
        party = SearchedParty(
            total = 0,
            parties = emptyList()
        ),
        partyRecruitment = SearchedPartyRecruitment(
            total = 0,
            partyRecruitments = emptyList()
        )
    ),

    val isLoadingParty: Boolean = false,
    val partySearchedList: PartyList = PartyList(total = 0, parties = emptyList()),
    val isActiveParty: String = "active",
    val isDescParty: Boolean = true,
    val isPartyTypeSheetOpen: Boolean = false,
    val selectedTypeListParty: List<String> = emptyList(),

    val isLoadingRecruitment: Boolean = false,
    val recruitmentSearchedList: RecruitmentList = RecruitmentList(total = 0, partyRecruitments = emptyList()),
    val isDescRecruitment: Boolean = true,
    val isPartyTypeSheetOpenRecruitment: Boolean = false,
    val selectedTypeListRecruitment: List<String> = emptyList(),
    val selectedTypeListSize: Int = 0
)
