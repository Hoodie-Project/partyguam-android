package com.party.presentation.screen.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.party.common.ServerApiResponse
import com.party.common.UIState
import com.party.common.component.searchTabList
import com.party.common.ui.theme.MEDIUM_PADDING_SIZE
import com.party.common.ui.theme.WHITE
import com.party.domain.model.room.KeywordModel
import com.party.domain.model.search.Search
import com.party.presentation.screen.search.component.SearchArea
import com.party.presentation.screen.search.component.keyword.RecentSearchedArea
import com.party.presentation.screen.search.component.search.SearchedDataContent
import com.party.presentation.screen.search.viewmodel.SearchViewModel

@Composable
fun SearchScreen(
    snackBarHostState: SnackbarHostState,
    navController: NavHostController,
    searchViewModel: SearchViewModel,
) {
    LaunchedEffect(Unit) {
        searchViewModel.getKeywordList()
    }

    // true 이면 키워드 영역을 보여주고, false 이면 검색 결과를 보여준다.
    var isShowKeywordArea by remember { mutableStateOf(true) }

    // 입력한 키워드
    var keyword by remember { mutableStateOf("") }

    val keywordList by searchViewModel.keywordList.collectAsStateWithLifecycle()

    val getSearchedResult by searchViewModel.searchedDataState.collectAsStateWithLifecycle()


    /*if(isShowKeywordArea){
        SearchScreenKeywordAreaContent(
            keyword = keyword,
            keywordList = keywordList,
            onValueChange = { keyword = it },
            searchAction = {
                searchViewModel.insertKeyword(keyword)

                searchViewModel.search(keyword, 1, 50)

                isShowKeywordArea = false
                //keyword = ""
            },
            onDelete = { searchViewModel.deleteKeyword(it) },
            onNavigationClick = { navController.popBackStack() },
            onAllDelete = { searchViewModel.allDeleteKeyword() }
        )
    }else {
        SearchedDataContent(
            getSearchedResult = getSearchedResult,
        )
    }*/

    SearchScreenContent(
        isShowKeywordArea = isShowKeywordArea,
        keyword = keyword,
        keywordList = keywordList,
        onValueChange = { keyword = it },
        searchAction = {
            searchViewModel.insertKeyword(keyword)

            searchViewModel.search(keyword, 1, 50)

            isShowKeywordArea = false
            //keyword = ""
        },
        onDelete = { searchViewModel.deleteKeyword(it) },
        onNavigationClick = { navController.popBackStack() },
        onAllDelete = { searchViewModel.allDeleteKeyword() },
        getSearchedResult = getSearchedResult
    )
}

@Composable
fun SearchScreenContent(
    isShowKeywordArea: Boolean,
    keyword: String,
    keywordList: List<KeywordModel>,
    onValueChange: (String) -> Unit,
    searchAction: () -> Unit,
    onDelete: (String) -> Unit,
    onNavigationClick: () -> Unit,
    onAllDelete: () -> Unit,
    getSearchedResult: UIState<ServerApiResponse<Search>>
) {
    var selectedTabText by remember {
        mutableStateOf(searchTabList[0])
    }

    Scaffold(
        topBar = {
            SearchArea(
                keyword = keyword,
                onValueChange = onValueChange,
                onNavigationClick = onNavigationClick,
                searchAction = searchAction
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
            if (isShowKeywordArea) {
                RecentSearchedArea(
                    keywordList = keywordList,
                    onDelete = onDelete,
                    onAllDelete = onAllDelete
                )
            } else {
                SearchedDataContent(
                    getSearchedResult = getSearchedResult,
                    selectedTabText = selectedTabText,
                    onTabClick = { selectedTabText = it }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SearchScreenContentPreview() {
    SearchScreenContent(
        isShowKeywordArea = true,
        keyword = "파티, 모집공고 이름을 검색해보세요.",
        keywordList = emptyList(),
        onValueChange = {},
        searchAction = {},
        onDelete = {},
        onNavigationClick = {},
        onAllDelete = {},
        getSearchedResult = UIState.Idle
    )
}

@Preview(showBackground = true)
@Composable
fun SearchScreenContentPreview1() {
    SearchScreenContent(
        isShowKeywordArea = true,
        keyword = "파티, 모집공고 이름을 검색해보세요.",
        keywordList = listOf(
            KeywordModel("파티"),
            KeywordModel("모집공고"),
            KeywordModel("이름")
        ),
        onValueChange = {},
        searchAction = {},
        onDelete = {},
        onNavigationClick = {},
        onAllDelete = {},
        getSearchedResult = UIState.Idle
    )
}