package com.party.presentation.screen.party_edit_recruitment.component

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.party.common.R
import com.party.common.component.icon.DrawableIconButton
import com.party.common.component.scaffold.ScaffoldCenterBar
import com.party.common.ui.theme.BLACK
import com.party.common.ui.theme.T2

@Composable
fun PartyEditRecruitmentScaffoldArea(
    onNavigationClick: () -> Unit,
    onShowHelpCard: (Boolean) -> Unit,
    onManageClick: () -> Unit,
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
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "모집 수정",
                    fontWeight = FontWeight.Bold,
                    fontSize = T2
                )
                DrawableIconButton(
                    icon = painterResource(id = R.drawable.help),
                    contentDescription = "help",
                    onClick = { onShowHelpCard(true) }
                )
            }

        },
        actionIcons = {
            DrawableIconButton(
                icon = painterResource(id = R.drawable.icon_menu),
                iconColor = BLACK,
                iconSize = 32.dp,
                contentDescription = "",
                onClick = onManageClick
            )
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun PartyEditRecruitmentScaffoldAreaPreview() {
    PartyEditRecruitmentScaffoldArea(
        onNavigationClick = {},
        onShowHelpCard = {},
        onManageClick = {}
    )
}