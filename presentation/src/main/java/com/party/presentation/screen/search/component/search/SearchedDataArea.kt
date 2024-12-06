package com.party.presentation.screen.search.component.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.party.common.HeightSpacer
import com.party.common.LoadingProgressBar
import com.party.common.ServerApiResponse
import com.party.common.ServerApiResponse.SuccessResponse
import com.party.common.UIState
import com.party.common.component.searchTabList
import com.party.domain.model.search.Party
import com.party.domain.model.search.PartyType
import com.party.domain.model.search.Position
import com.party.domain.model.search.Search
import com.party.domain.model.search.SearchedParty
import com.party.domain.model.search.SearchedPartyData
import com.party.domain.model.search.SearchedPartyRecruitment
import com.party.domain.model.search.SearchedRecruitmentData
import com.party.presentation.screen.search.SearchState

@Composable
fun SearchedDataContent(
    searchState: SearchState,
    onTabClick: (String) -> Unit,
    onPartyTypeApply: (MutableList<String>) -> Unit,
) {
    when {
        searchState.isLoadingAllSearch -> { LoadingProgressBar() }
        !searchState.isLoadingAllSearch -> {
            AllSearchedContent(
                searchState = searchState,
                onTabClick = onTabClick
            )
        }
    }
}

@Composable
private fun AllSearchedContent(
    searchState: SearchState,
    onTabClick: (String) -> Unit,
) {
    Column {
        SearchTopTabArea(
            modifier = Modifier.height(48.dp),
            searchTabList = searchTabList,
            selectedTabText = searchState.selectedTabText,
            onTabClick = onTabClick
        )

        HeightSpacer(heightDp = 24.dp)

        when(searchState.selectedTabText){
            searchTabList[0] -> {
                SearchEntireArea(
                    partyList = searchState.allSearchedList.party.parties,
                    recruitmentList = searchState.allSearchedList.partyRecruitment.partyRecruitments
                )
            }
            /*searchTabList[1] -> {
                SearchPartyArea(
                    partyList = searchedParty?.parties ?: listOf(),
                    onPartyTypeApply = onPartyTypeApply
                )
            }
            searchTabList[2] -> {
                SearchRecruitmentArea(
                    recruitmentList = searchedPartyRecruitment?.partyRecruitments ?: listOf()
                )
            }*/
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SearchedDataContentPreview1() {
    SearchedDataContent(
        searchState = SearchState(),
        onTabClick = {},
        onPartyTypeApply = {}
    )
}