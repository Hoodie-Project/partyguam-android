package com.party.presentation.screen.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
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
import com.party.common.ui.theme.MEDIUM_PADDING_SIZE
import com.party.common.ui.theme.WHITE
import com.party.domain.model.room.KeywordModel
import com.party.presentation.screen.search.component.RecentSearchedArea
import com.party.presentation.screen.search.component.SearchArea
import com.party.presentation.screen.search.viewmodel.SearchViewModel

@Composable
fun SearchScreen(
    navController: NavHostController,
    searchViewModel: SearchViewModel
) {
    // 입력한 키워드
    var keyword by remember { mutableStateOf("") }

    val keywordList by searchViewModel.keywordList.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        searchViewModel.getKeywordList()
    }

    SearchScreenContent(
        keyword = keyword,
        keywordList = keywordList,
        onValueChange = { keyword = it },
        searchAction = {
            searchViewModel.insertKeyword(keyword)
            keyword = ""
        },
        onDelete = { searchViewModel.deleteKeyword(it) },
        onNavigationClick = { navController.popBackStack() },
        onAllDelete = { searchViewModel.allDeleteKeyword() }
    )
}

@Composable
fun SearchScreenContent(
    keyword: String,
    keywordList: List<KeywordModel>,
    onValueChange: (String) -> Unit,
    searchAction: () -> Unit,
    onDelete: (String) -> Unit,
    onNavigationClick: () -> Unit,
    onAllDelete: () -> Unit
) {
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
            RecentSearchedArea(
                keywordList = keywordList,
                onDelete = onDelete,
                onAllDelete = onAllDelete
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SearchScreenContentPreview1() {
    SearchScreenContent(
        keyword = "파티, 모집공고 이름을 검색해보세요.",
        keywordList = emptyList(),
        onValueChange = {},
        searchAction = {},
        onDelete = {},
        onNavigationClick = {},
        onAllDelete = {}
    )
}

@Preview(showBackground = true)
@Composable
fun SearchScreenContentPreview2() {
    SearchScreenContent(
        keyword = "파티, 모집공고 이름을 검색해보세요.",
        keywordList = listOf(
            KeywordModel("파티"),
            KeywordModel("모집공고"),
            KeywordModel("이름"),
            KeywordModel("모집공고"),
            KeywordModel("모집공고"),
        ),
        onValueChange = {},
        searchAction = {},
        onDelete = {},
        onNavigationClick = {},
        onAllDelete = {}
    )
}