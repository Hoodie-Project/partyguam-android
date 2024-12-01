package com.party.presentation.screen.home

import androidx.compose.runtime.Composable
import com.party.common.component.TabArea

@Composable
fun HomeTopTabArea(
    homeTopTabList: List<String>,
    selectedTabText: String,
    onTabClick: (String) -> Unit,
) {
    TabArea(
        tabList = homeTopTabList,
        selectedTabText = selectedTabText,
        onTabClick = onTabClick
    )
}