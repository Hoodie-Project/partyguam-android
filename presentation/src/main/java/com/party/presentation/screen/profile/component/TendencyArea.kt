package com.party.presentation.screen.profile.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.party.common.HeightSpacer
import com.party.common.TextComponent
import com.party.common.WidthSpacer
import com.party.common.ui.theme.T2
import com.party.common.ui.theme.T3
import com.party.domain.model.user.profile.UserPersonality

@Composable
fun TendencyArea(
    userTendencyList: List<UserPersonality>
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

        userTendencyList.forEachIndexed { index, userPersonality ->
            UserTendencyItem(
                userPersonality = userPersonality,
            )
            HeightSpacer(heightDp = 8.dp)
        }
    }
}

@Composable
private fun UserTendencyItem(
    userPersonality: UserPersonality,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "·")
        WidthSpacer(widthDp = 4.dp)
        TextComponent(
            text = userPersonality.personalityOption.content,
            fontSize = T3,
        )
    }
}