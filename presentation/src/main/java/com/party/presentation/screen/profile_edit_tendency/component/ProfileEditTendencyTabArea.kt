package com.party.presentation.screen.profile_edit_tendency.component

import androidx.compose.runtime.Composable
import com.party.common.component.TabArea

@Composable
fun ProfileEditTendencyTabArea(
    profileEditTendencyTabList: List<String>,
    selectedTabText: String,
    onTabClick: (String) -> Unit,
) {
    TabArea(
        tabList = profileEditTendencyTabList,
        selectedTabText = selectedTabText,
        onTabClick = onTabClick
    )
}