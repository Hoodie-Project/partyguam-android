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

            val searchedParty = resultData.data?.party
            val searchedPartyRecruitment = resultData.data?.partyRecruitment
            Column {
                SearchTopTabArea(
                    modifier = Modifier.height(48.dp),
                    searchTabList = searchTabList,
                    selectedTabText = selectedTabText,
                    onTabClick = onTabClick
                )

                HeightSpacer(heightDp = 24.dp)

                when(selectedTabText){
                    searchTabList[0] -> {
                        SearchEntireArea(
                            partyList = searchedParty?.parties ?: listOf(),
                            recruitmentList = searchedPartyRecruitment?.partyRecruitments ?: listOf()
                        )
                    }
                    searchTabList[1] -> {
                        SearchPartyArea(
                            partyList = searchedParty?.parties ?: listOf(),
                            onPartyTypeApply = onPartyTypeApply
                        )
                    }
                    searchTabList[2] -> {
                        SearchRecruitmentArea(
                            recruitmentList = searchedPartyRecruitment?.partyRecruitments ?: listOf()
                        )
                    }
                }
            }
        }
        is UIState.Error -> {}
        is UIState.Exception -> {}
    }
}

@Preview(showBackground = true)
@Composable
fun SearchedDataContentPreview1() {
    SearchedDataContent(
        getSearchedResult = UIState.Success(
            SuccessResponse(
                Search(
                    party = SearchedParty(
                        total = 7763,
                        parties = listOf(
                            SearchedPartyData(
                                id = 4569,
                                partyType = PartyType(id = 3829, type = "ultrices"),
                                tag = "dui",
                                title = "eruditi",
                                content = "mucius",
                                image = "tibique",
                                status = "consetetur",
                                createdAt = "his",
                                updatedAt = "interdum",
                                recruitmentCount = 9436
                            )
                        )
                    ), partyRecruitment = SearchedPartyRecruitment(
                        total = 9140,
                        partyRecruitments = listOf(
                            SearchedRecruitmentData(
                                id = 9617,
                                content = "est",
                                recruitingCount = 2679,
                                recruitedCount = 1813,
                                createdAt = "autem",
                                party = Party(
                                    id = 3576,
                                    title = "duo",
                                    image = "commune",
                                    partyType = PartyType(id = 7057, type = "audire")
                                ),
                                position = Position(
                                    id = 3399,
                                    main = "possit",
                                    sub = "voluptatibus"
                                )
                            )
                        )
                    )
                )
            )
        ),
        selectedTabText = searchTabList[0],
        onTabClick = {},
        onPartyTypeApply = {}
    )
}