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
    onChangeOrderByRecruitment: (Boolean) -> Unit,
    onPartyTypeModel: (Boolean) -> Unit,
    onPartyTypeRecruitment: (Boolean) -> Unit,
    onPartyTypeItem1: (String) -> Unit,
    onPartyTypeItem2: (String) -> Unit,
    onReset: () -> Unit,
    onPartyTypeApply: () -> Unit,
    onPartyTypeApply2: () -> Unit,
    onPositionSheetClose: (Boolean) -> Unit,
    onPositionSheetClick: () -> Unit,
    onMainPositionClick: (String) -> Unit,
    onSubPositionClick: (String) -> Unit,
    onDelete: (Pair<String, String>) -> Unit,
    onPositionApply: () -> Unit
) {
    println("searchState ${searchState.allSearchedList}")
    Column {
        SearchTopTabArea(
            modifier = Modifier.height(48.dp),
            searchTabList = searchTabList,
            selectedTabText = searchState.selectedTabText,
            onTabClick = onTabClick
        )

        HeightSpacer(heightDp = 20.dp)

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
                    onClick = onPartyTypeItem1,
                    onReset = onReset,
                    onPartyTypeApply = onPartyTypeApply
                )
            }
            searchTabList[2] -> {
                SearchRecruitmentArea(
                    searchState = searchState,
                    onPartyTypeModel = onPartyTypeRecruitment,
                    onChangeOrderBy = onChangeOrderByRecruitment,
                    onClick = onPartyTypeItem2,
                    onReset = onReset,
                    onPartyTypeApply2 = onPartyTypeApply2,
                    onPositionSheetClose = onPositionSheetClose,
                    onPositionSheetClick = onPositionSheetClick,
                    onMainPositionClick = onMainPositionClick,
                    onSubPositionClick = onSubPositionClick,
                    onDelete = onDelete,
                    onPositionApply = onPositionApply
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
        onPartyTypeItem1 = {},
        onReset = {},
        onChangeOrderByRecruitment = {},
        onPartyTypeRecruitment = {},
        onPartyTypeItem2 = {},
        onPartyTypeApply2 = {},
        onPositionSheetClose = {},
        onPositionSheetClick = {},
        onMainPositionClick = {},
        onSubPositionClick = {},
        onDelete = {},
        onPositionApply = {}
    )
}