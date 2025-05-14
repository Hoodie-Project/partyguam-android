package com.party.presentation.screen.search

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.party.common.Screens
import com.party.common.component.BottomNavigationBar
import com.party.common.ui.theme.MEDIUM_PADDING_SIZE
import com.party.common.ui.theme.WHITE
import com.party.domain.model.room.KeywordModel
import com.party.domain.model.search.Party
import com.party.domain.model.search.PartyType
import com.party.domain.model.search.Position
import com.party.domain.model.search.Search
import com.party.domain.model.search.SearchedParty
import com.party.domain.model.search.SearchedPartyRecruitment
import com.party.domain.model.search.SearchedRecruitmentData
import com.party.presentation.screen.search.component.SearchArea
import com.party.presentation.screen.search.component.keyword.RecentSearchedArea
import com.party.presentation.screen.search.component.search.SearchedDataContent
import com.party.presentation.screen.search.viewmodel.SearchViewModel

@Composable
fun SearchRoute(
    context: Context,
    navController: NavHostController,
    searchViewModel: SearchViewModel = hiltViewModel(),
) {
    LaunchedEffect(Unit) {
        searchViewModel.getKeywordList()
    }
    
    val searchState by searchViewModel.searchState.collectAsStateWithLifecycle()

    SearchScreen(
        context = context,
        navController = navController,
        searchState = searchState,
        onAction = { action ->
            searchViewModel.onAction(action)
        }
    )
}

@Composable
fun SearchScreen(
    context: Context,
    navController: NavHostController,
    searchState: SearchState,
    onAction: (SearchAction) -> Unit
) {
    Scaffold(
        topBar = {
            SearchArea(
                keyword = searchState.inputKeyword,
                onValueChange = { onAction(SearchAction.OnInputKeywordChange(it)) },
                onNavigationClick = { onAction(SearchAction.OnNavigationBack) },
                searchAction = { onAction(SearchAction.OnSearch) }
            )
        },
        bottomBar = {
            BottomNavigationBar(
                context = context,
                navController = navController,
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(WHITE)
                .padding(it)
                .padding(horizontal = MEDIUM_PADDING_SIZE)
        ) {
            if (searchState.isShowKeywordArea) {
                RecentSearchedArea(
                    keywordList = searchState.keywordList,
                    onAllDelete = { onAction(SearchAction.OnAllDeleteKeyword) },
                    onDelete = { keyword -> onAction(SearchAction.OnDeleteKeyword(keyword)) },
                )
            } else {
                SearchedDataContent(
                    searchState = searchState,
                    onTabClick = { selectedTab -> onAction(SearchAction.OnTabClick(selectedTab)) },
                    onChangeOrderByParty = { isDesc -> onAction(SearchAction.OnChangeOrderByParty(isDesc)) },
                    onToggle = { status -> onAction(SearchAction.OnChangeActive(status)) },
                    onPartyTypeModel = { isVisible -> onAction(SearchAction.OnPartyTypeModelClose(isVisible)) },
                    onPartyTypeItem1 = { selectedPartyType -> onAction(SearchAction.OnSelectedPartyType(selectedPartyType)) },
                    onReset = { onAction(SearchAction.OnPartyTypeReset) },
                    onPartyTypeApply = { onAction(SearchAction.OnPartyTypeApply) },
                    onChangeOrderByRecruitment = { isDesc -> onAction(SearchAction.OnChangeOrderByRecruitment(isDesc)) },
                    onPartyTypeRecruitment = { isVisible -> onAction(SearchAction.OnPartyTypeRecruitment(isVisible)) },
                    onPartyTypeItem2 = { selectedPartyType -> onAction(SearchAction.OnSelectedPartyTypeRecruitment(selectedPartyType)) },
                    onPartyTypeApply2 = { onAction(SearchAction.OnPartyTypeApply2) },
                    onPositionSheetClose = { isVisible -> onAction(SearchAction.OnPositionSheetClose(isVisible)) },
                    onPositionSheetClick = { onAction(SearchAction.OnPositionSheetOpenClick) },
                    onMainPositionClick = { selectedMainPosition -> onAction(SearchAction.OnMainPositionClick(selectedMainPosition)) },
                    onSubPositionClick = { selectedSubPosition -> onAction(SearchAction.OnSubPositionClick(selectedSubPosition)) },
                    onDelete = { position -> onAction(SearchAction.OnDelete(position)) },
                    onPositionApply = { onAction(SearchAction.OnPositionApply) },
                    onPartyClick = { partyId -> navController.navigate(Screens.PartyDetail(partyId = partyId))},
                    onRecruitmentClick = { recruitmentId,  partyId -> navController.navigate(Screens.RecruitmentDetail(partyRecruitmentId = recruitmentId, partyId = partyId))}
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SearchScreenPreview() {
    SearchScreen(
        context = LocalContext.current,
        navController = rememberNavController(),
        searchState = SearchState(),
        onAction = {}
    )
}

@Preview(showBackground = true)
@Composable
private fun SearchScreenPreview1() {
    SearchScreen(
        context = LocalContext.current,
        navController = rememberNavController(),
        searchState = SearchState(
            keywordList = listOf(
                KeywordModel(keyword = "파티"),
                KeywordModel(keyword = "모집공고"),
                KeywordModel(keyword = "이름")
            )
        ),
        onAction = {}
    )
}

@Preview(showBackground = true)
@Composable
private fun SearchScreenPreview2() {
    SearchScreen(
        context = LocalContext.current,
        navController = rememberNavController(),
        searchState = SearchState(
            isShowKeywordArea = false,
            isLoadingAllSearch = true
        ),
        onAction = {}
    )
}

@Preview(showBackground = true)
@Composable
private fun SearchScreenPreview3() {
    SearchScreen(
        context = LocalContext.current,
        navController = rememberNavController(),
        searchState = SearchState(
            isShowKeywordArea = false,
            isLoadingAllSearch = false,
            allSearchedList = Search(
                party = SearchedParty(
                    total = 1225,
                    parties = listOf()
                ),
                partyRecruitment = SearchedPartyRecruitment(
                    total = 3656,
                    partyRecruitments = listOf(
                        SearchedRecruitmentData(
                            id = 3644,
                            content = "pertinacia",
                            recruitingCount = 2710,
                            recruitedCount = 2875,
                            createdAt = "in",
                            party = Party(
                                id = 4589,
                                title = "같이 개발 할 사람",
                                image = "verear",
                                status = "active",
                                partyType = PartyType(id = 7121, type = "포트폴리오")
                            ),
                            status = "active",
                            position = Position(
                                id = 1907,
                                main = "개발자",
                                sub = "안드로이드",
                            )
                        )
                    )
                )
            )
        ),
        onAction = {}
    )
}