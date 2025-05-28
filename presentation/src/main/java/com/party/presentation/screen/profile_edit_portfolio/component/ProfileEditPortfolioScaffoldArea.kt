package com.party.presentation.screen.profile_edit_portfolio.component

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.party.common.R
import com.party.common.component.icon.DrawableIconButton
import com.party.common.component.scaffold.ScaffoldCenterBar
import com.party.guam.design.BLACK
import com.party.guam.design.T2

@Composable
fun ProfileEditPortfolioScaffoldArea(
    onNavigationClick: () -> Unit,
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
        title = {
            Text(
                text = "이력서 및 포트폴리오 링크",
                fontWeight = FontWeight.Bold,
                fontSize = T2
            )
        },
    )
}

@Preview(showBackground = true)
@Composable
private fun ProfileEditPortfolioScaffoldAreaPreview() {
    ProfileEditPortfolioScaffoldArea(
        onNavigationClick = {}
    )
}