package com.party.presentation.screen.profile_edit.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.party.common.utils.HeightSpacer
import com.party.common.utils.TextComponent
import com.party.common.utils.WidthSpacer
import com.party.common.ui.theme.T3
import com.party.domain.model.user.profile.PersonalityOption
import com.party.domain.model.user.profile.PersonalityQuestion
import com.party.domain.model.user.profile.UserPersonality

@Composable
fun UserTendencyArea(
    userTendencyList: List<UserPersonality>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
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

@Preview(showBackground = true)
@Composable
private fun UserTendencyAreaPreview() {
    UserTendencyArea(
        userTendencyList = listOf(
            UserPersonality(
                id = 1,
                personalityOption = PersonalityOption(
                    id = 1,
                    content = "content",
                    personalityQuestion = PersonalityQuestion(
                        id = 1,
                        content = "content",
                        responseCount = 1
                    )
                )
            )
        )
    )
}