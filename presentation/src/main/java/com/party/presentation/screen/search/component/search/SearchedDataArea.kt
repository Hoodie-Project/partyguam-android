package com.party.presentation.screen.search.component.search

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.party.common.HeightSpacer
import com.party.common.LoadingProgressBar
import com.party.common.ServerApiResponse
import com.party.common.ServerApiResponse.SuccessResponse
import com.party.common.UIState
import com.party.common.component.searchTabList
import com.party.domain.model.search.Search

@Composable
fun SearchedDataContent(
    getSearchedResult: UIState<ServerApiResponse<Search>>,
    selectedTabText: String,
    onTabClick: (String) -> Unit,
    onPartyTypeApply: (MutableList<String>) -> Unit,
) {

    when(getSearchedResult){
        is UIState.Idle -> {}
        is UIState.Loading -> { LoadingProgressBar() }
        is UIState.Success -> {
            val resultData = getSearchedResult.data as SuccessResponse<Search>


        }
        is UIState.Error -> {}
        is UIState.Exception -> {}
    }


    Column {
        HeightSpacer(heightDp = 24.dp)
        SearchTopTabArea(
            searchTabList = searchTabList,
            selectedTabText = selectedTabText,
            onTabClick = onTabClick
        )

        HeightSpacer(heightDp = 24.dp)

        when(selectedTabText){
            searchTabList[0] -> {
                SearchEntireArea(
                    partyList = listOf(),
                    recruitmentList = listOf()
                )
            }
            searchTabList[1] -> {
                SearchPartyArea(
                    partyList = listOf(),
                    onPartyTypeApply = onPartyTypeApply
                )
            }
            searchTabList[2] -> {
                SearchRecruitmentArea(
                    recruitmentList = listOf()
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SearchedDataContentPreview1() {
    SearchedDataContent(
        getSearchedResult = UIState.Idle,
        selectedTabText = searchTabList[0],
        onTabClick = {},
        onPartyTypeApply = {}
    )
}

@Preview(showBackground = true)
@Composable
fun SearchedDataContentPreview2() {
    SearchedDataContent(
        getSearchedResult = UIState.Idle,
        selectedTabText = searchTabList[1],
        onTabClick = {},
        onPartyTypeApply = {}
    )
}