package com.party.presentation.screen.recruitment_detail.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.party.common.R
import com.party.common.component.icon.DrawableIconButton
import com.party.common.component.scaffold.ScaffoldCenterBar
import com.party.common.ui.theme.BLACK

@Composable
fun RecruitmentScaffoldArea(
    onNavigationClick: () -> Unit,
    onSharedClick: () -> Unit,
) {
    ScaffoldCenterBar(
        navigationIcon = {
            DrawableIconButton(
                icon = painterResource(id = R.drawable.icon_arrow_back),
                iconColor = BLACK,
                iconSize = 24.dp,
                contentDescription = "back",
                onClick = { onNavigationClick() }
            )
        },
        actionIcons = {
            DrawableIconButton(
                icon = painterResource(id = R.drawable.icon_share),
                iconColor = BLACK,
                iconSize = 24.dp,
                contentDescription = "share",
                onClick = { onSharedClick() }
            )
        }
    )
}