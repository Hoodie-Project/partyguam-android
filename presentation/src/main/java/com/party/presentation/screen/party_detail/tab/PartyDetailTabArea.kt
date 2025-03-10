package com.party.presentation.screen.party_detail.tab

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.party.common.component.TabArea

@Composable
fun PartyDetailTabArea(
    partyDetailTabList: List<String>,
    selectedTabText: String,
    onTabClick: (String) -> Unit,
) {
    TabArea(
        modifier = Modifier.padding(start = 20.dp),
        tabList = partyDetailTabList,
        selectedTabText = selectedTabText,
        onTabClick = onTabClick
    )
}