package com.party.presentation.screen.search.component.search

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
) {
    var selectedTabText by remember {
        mutableStateOf(searchTabList[0])
    }

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
            onTabClick = { selectedTabText = it }
        )
    }


}

@Preview(showBackground = true)
@Composable
fun SearchedDataContentPreview() {
    SearchedDataContent(
        getSearchedResult = UIState.Idle
    )
}