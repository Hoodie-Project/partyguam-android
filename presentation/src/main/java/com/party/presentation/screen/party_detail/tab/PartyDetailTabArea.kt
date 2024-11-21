package com.party.presentation.screen.party_detail.tab

import androidx.compose.runtime.Composable
import com.party.common.component.TabArea

@Composable
fun PartyDetailTabArea(
    partyDetailTabList: List<String>,
    selectedTabText: String,
    onTabClick: (String) -> Unit,
) {
    TabArea(
        tabList = partyDetailTabList,
        selectedTabText = selectedTabText,
        onTabClick = onTabClick
    )
}