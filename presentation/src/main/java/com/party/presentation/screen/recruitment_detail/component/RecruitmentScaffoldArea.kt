package com.party.presentation.screen.recruitment_detail.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.party.common.R
import com.party.common.component.icon.DrawableIconButton
import com.party.common.component.scaffold.ScaffoldCenterBar
import com.party.common.ui.theme.BLACK
import com.party.presentation.enum.PartyAuthorityType
import com.party.presentation.screen.recruitment_detail.RecruitmentDetailState

@Composable
fun RecruitmentScaffoldArea(
    recruitmentDetailState: RecruitmentDetailState,
    onNavigationClick: () -> Unit,
    onSharedClick: () -> Unit,
    onManageClick: () -> Unit,
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
            /*DrawableIconButton(
                icon = painterResource(id = R.drawable.icon_share),
                iconColor = BLACK,
                iconSize = 24.dp,
                contentDescription = "share",
                onClick = { onSharedClick() }
            )*/

            if(recruitmentDetailState.partyAuthority.authority == PartyAuthorityType.MASTER.authority) {
                DrawableIconButton(
                    icon = painterResource(id = R.drawable.icon_setting),
                    iconColor = BLACK,
                    iconSize = 24.dp,
                    contentDescription = "setting",
                    onClick = onManageClick
                )
            }
        }
    )
}