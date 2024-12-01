package com.party.presentation.screen.state.component

import androidx.compose.runtime.Composable
import com.party.common.component.TabArea
import com.party.common.ui.theme.PRIMARY

@Composable
fun StateTabArea(
    searchTabList: List<String>,
    selectedTabText: String,
    onTabClick: (String) -> Unit,
) {
    TabArea(
        tabList = searchTabList,
        selectedTabText = selectedTabText,
        onTabClick = onTabClick,
        selectedTabColor = PRIMARY,
    )
}