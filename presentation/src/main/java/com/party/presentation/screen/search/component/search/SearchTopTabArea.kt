package com.party.presentation.screen.search.component.search

import androidx.compose.runtime.Composable
import com.party.common.component.TabArea
import com.party.common.ui.theme.BLACK

@Composable
fun SearchTopTabArea(
    searchTabList: List<String>,
    selectedTabText: String,
    onTabClick: (String) -> Unit,
) {
    TabArea(
        tabList = searchTabList,
        selectedTabText = selectedTabText,
        onTabClick = onTabClick,
        selectedTabColor = BLACK,
    )
}