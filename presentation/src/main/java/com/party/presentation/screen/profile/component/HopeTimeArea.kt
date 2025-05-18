package com.party.presentation.screen.profile.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.party.common.ui.theme.T2
import com.party.common.ui.theme.T3
import com.party.common.utils.HeightSpacer
import com.party.common.utils.TextComponent
import com.party.common.utils.WidthSpacer
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

        Column {
            userPersonalityList.forEachIndexed { _, userPersonality ->

                TextComponent(
                    modifier = Modifier
                        .height(22.dp),
                    text = "·${userPersonality.personalityOption.content}",
                    fontSize = T3,
                    fontWeight = FontWeight.Normal,
                )


                WidthSpacer(widthDp = 8.dp)
            }
        }
    }
}