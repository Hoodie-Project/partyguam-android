package com.party.presentation.screen.search.component.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.party.common.HeightSpacer
import com.party.common.component.searchTabList
import com.party.presentation.screen.search.SearchState

@Composable
fun SearchedDataContent(
    searchState: SearchState,
    onTabClick: (String) -> Unit,
    onToggle: (String) -> Unit,
    onChangeOrderByParty: (Boolean) -> Unit,
    onPartyTypeModel: (Boolean) -> Unit,
    onClick: (String) -> Unit,
    onReset: () -> Unit,
    onPartyTypeApply: () -> Unit
) {
    Column {
        SearchTopTabArea(
            modifier = Modifier.height(48.dp),
            searchTabList = searchTabList,
            selectedTabText = searchState.selectedTabText,
            onTabClick = onTabClick
        )

        HeightSpacer(heightDp = 24.dp)

        when(searchState.selectedTabText) {
            searchTabList[0] -> {
                SearchEntireArea(
                    partyList = searchState.allSearchedList.party.parties,
                    recruitmentList = searchState.allSearchedList.partyRecruitment.partyRecruitments
                )
            }
            searchTabList[1] -> {
                SearchPartyArea(
                    searchState = searchState,
                    onChangeOrderBy = onChangeOrderByParty,
                    onToggle = onToggle,
                    onPartyTypeModel = onPartyTypeModel,
                    onClick = onClick,
                    onReset = onReset,
                    onPartyTypeApply = onPartyTypeApply
                )
            }
            searchTabList[2] -> {
                SearchRecruitmentArea(
                    recruitmentList = searchState.recruitmentSearchedList.partyRecruitments
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SearchedDataContentPreview1() {
    SearchedDataContent(
        searchState = SearchState(
            selectedTabText = "전체"
        ),
        onTabClick = {},
        onPartyTypeApply = {},
        onChangeOrderByParty = {},
        onToggle = {},
        onPartyTypeModel = {},
        onClick = {},
        onReset = {}
    )
}