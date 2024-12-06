package com.party.presentation.screen.search

import com.party.common.component.bottomsheet.list.positionList
import com.party.domain.model.party.PartyList
import com.party.domain.model.party.RecruitmentList
import com.party.domain.model.room.KeywordModel
import com.party.domain.model.search.Search
import com.party.domain.model.search.SearchedParty
import com.party.domain.model.search.SearchedPartyRecruitment
import com.party.domain.model.user.detail.PositionList

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
    val isPositionSheetOpen : Boolean = false,
    val isPartyTypeSheetOpenRecruitment: Boolean = false,
    val isDescRecruitment: Boolean = true,
    val selectedTypeListRecruitment: List<String> = emptyList(),
    val selectedTypeListSize: Int = 0,

    val selectedMainPosition: String = positionList[0],
    val isLoadingSubPosition : Boolean = false,
    val getSubPositionList: List<PositionList> = emptyList(),
    val selectedSubPositionList: List<PositionList> = emptyList(),

    val selectedMainAndSubPosition: List<Pair<String, String>> = emptyList(),
)
