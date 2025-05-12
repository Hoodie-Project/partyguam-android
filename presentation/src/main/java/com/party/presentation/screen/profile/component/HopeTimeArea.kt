package com.party.presentation.screen.profile.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.party.common.utils.HeightSpacer
import com.party.common.utils.TextComponent
import com.party.common.utils.WidthSpacer
import com.party.common.component.chip.Chip
import com.party.common.ui.theme.LIGHT400
import com.party.common.ui.theme.T2
import com.party.common.ui.theme.T3
import com.party.domain.model.user.profile.UserPersonality

@Composable
fun HopeTimeArea(
    userPersonalityList: List<UserPersonality>,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        TextComponent(
            text = "희망 시간",
            fontSize = T2,
            fontWeight = FontWeight.SemiBold,
        )
        HeightSpacer(heightDp = 20.dp)

        Row {
            userPersonalityList.forEachIndexed { _, userPersonality ->
                Chip(
                    text = userPersonality.personalityOption.content,
                    containerColor = LIGHT400,
                    fontWeight = FontWeight.Normal,
                    fontSize = T3,
                )
                WidthSpacer(widthDp = 8.dp)
            }
        }
    }
}