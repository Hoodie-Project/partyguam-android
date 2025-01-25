package com.party.presentation.screen.party_detail.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.party.common.R
import com.party.common.component.icon.DrawableIconButton
import com.party.common.component.scaffold.ScaffoldCenterBar
import com.party.common.ui.theme.BLACK
import com.party.presentation.enum.PartyAuthorityType
import com.party.presentation.screen.party_detail.PartyDetailState

@Composable
fun PartyDetailScaffoldArea(
    state: PartyDetailState,
    onNavigationClick: () -> Unit,
    onSharedClick: () -> Unit,
    onManageClick: () -> Unit,
    onMoreClick: () -> Unit,
) {
    ScaffoldCenterBar(
        navigationIcon = {
            DrawableIconButton(
                icon = painterResource(id = R.drawable.icon_arrow_back),
                iconColor = BLACK,
                iconSize = 24.dp,
                contentDescription = "back",
                onClick = onNavigationClick
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
                    onClick = onSharedClick
                )

                if(state.partyAuthority.authority == PartyAuthorityType.MASTER.authority) {
                    DrawableIconButton(
                        icon = painterResource(id = R.drawable.icon_setting),
                        iconColor = BLACK,
                        iconSize = 24.dp,
                        contentDescription = "setting",
                        onClick = onManageClick
                    )
                }

                if(state.partyAuthority.authority == PartyAuthorityType.MEMBER.authority) {
                    DrawableIconButton(
                        icon = painterResource(id = R.drawable.icon_more),
                        iconColor = BLACK,
                        iconSize = 24.dp,
                        contentDescription = "more",
                        onClick = onMoreClick
                    )
                }
            }
        }
    )
}