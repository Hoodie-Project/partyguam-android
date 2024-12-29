package com.party.presentation.screen.profile_edit.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.party.common.HeightSpacer
import com.party.common.TextComponent
import com.party.common.ui.theme.B1
import com.party.common.ui.theme.LINK_COLOR

@Composable
fun ProfileEditPortfolioArea(
    portfolioLink: String?,
) {
    HeightSpacer(heightDp = 20.dp)
    TextComponent(
        text = portfolioLink ?: "",
        fontSize = B1,
        textDecoration = TextDecoration.Underline,
        textColor = LINK_COLOR
    )
}

@Preview(showBackground = true)
@Composable
private fun ProfileEditPortfolioAreaPreview() {
    ProfileEditPortfolioArea(
        portfolioLink = "https://www.naver.com"
    )
}