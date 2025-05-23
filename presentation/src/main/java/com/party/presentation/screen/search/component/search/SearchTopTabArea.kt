package com.party.presentation.screen.search.component.search

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.party.common.component.TabArea

@Composable
fun SearchTopTabArea(
    modifier: Modifier,
    searchTabList: List<String>,
    selectedTabText: String,
    onTabClick: (String) -> Unit,
) {
    TabArea(
        modifier = modifier,
        tabList = searchTabList,
        selectedTabText = selectedTabText,
        onTabClick = onTabClick,
    )
}