package com.party.presentation.screen.party_detail.component

import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.party.common.R
import com.party.common.component.icon.DrawableIconButton
import com.party.common.component.scaffold.ScaffoldCenterBar
import com.party.common.ui.theme.BLACK

@Composable
fun PartyDetailScaffoldArea(
    onNavigationClick: () -> Unit,
    onSharedClick: () -> Unit,
    onMoreClick: () -> Unit,
) {
    ScaffoldCenterBar(
        navigationIcon = {
            DrawableIconButton(
                icon = painterResource(id = R.drawable.arrow_back),
                iconColor = BLACK,
                iconSize = 24.dp,
                contentDescription = "back",
                onClick = { onNavigationClick() }
            )
        },
        actionIcons = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                DrawableIconButton(
                    icon = painterResource(id = R.drawable.icon_share),
                    iconColor = BLACK,
                    iconSize = 24.dp,
                    contentDescription = "share",
                    onClick = { onSharedClick() }
                )
                DrawableIconButton(
                    icon = painterResource(id = R.drawable.icon_more),
                    iconColor = BLACK,
                    iconSize = 24.dp,
                    contentDescription = "more",
                    onClick = { onMoreClick() }
                )
            }
        }
    )
}