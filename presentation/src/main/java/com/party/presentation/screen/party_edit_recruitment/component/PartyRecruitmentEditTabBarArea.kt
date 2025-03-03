package com.party.presentation.screen.party_edit_recruitment.component

import androidx.compose.runtime.Composable
import com.party.common.component.TabArea

@Composable
fun PartyRecruitmentEditTabBarArea(
    topTabList: List<String>,
    selectedTabText: String,
    onTabClick: (String) -> Unit,
) {
    TabArea(
        tabList = topTabList,
        selectedTabText = selectedTabText,
        onTabClick = onTabClick
    )
}