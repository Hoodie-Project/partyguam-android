package com.party.presentation.screen.search

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
    )
)