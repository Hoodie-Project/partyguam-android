package com.party.presentation.screen.party_create.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.party.common.HeightSpacer
import com.party.common.R
import com.party.common.TextComponent
import com.party.common.WidthSpacer
import com.party.common.component.icon.DrawableIconButton
import com.party.common.ui.theme.B1
import com.party.common.ui.theme.GRAY400
import com.party.common.ui.theme.T2

@Composable
fun PartyCreateDescriptionArea(
    title: String,
    description: String,
    icon: @Composable () -> Unit = {},
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            TextComponent(
                modifier = Modifier
                    .wrapContentWidth(),
                text = title,
                fontSize = T2,
                fontWeight = FontWeight.SemiBold,
            )
            WidthSpacer(widthDp = 4.dp)
            icon()
        }
        HeightSpacer(heightDp = 8.dp)
        TextComponent(
            modifier = Modifier
                .fillMaxWidth(),
            text = description,
            fontSize = B1,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PartyCreateDescriptionAreaPreview() {
    PartyCreateDescriptionArea(
        title = "파티 소개글",
        description = "파티의 방향성, 참고 사항 등을 자유롭게 적어주세요",
        icon = {
            DrawableIconButton(
                modifier = Modifier
                    .size(20.dp),
                icon = painterResource(id = R.drawable.icon_help),
                contentDescription = "help",
                iconColor = GRAY400,
                onClick = { }
            )
        }
    )
}