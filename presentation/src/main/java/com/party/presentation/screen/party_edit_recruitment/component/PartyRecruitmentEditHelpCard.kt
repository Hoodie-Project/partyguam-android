package com.party.presentation.screen.party_edit_recruitment.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.party.common.HeightSpacer
import com.party.common.R
import com.party.common.TextComponent
import com.party.common.component.custom_tooltip.TriangleEdge
import com.party.common.component.icon.DrawableIconButton
import com.party.common.ui.theme.B2
import com.party.common.ui.theme.GRAY500
import com.party.common.ui.theme.LARGE_CORNER_SIZE
import com.party.common.ui.theme.T3
import com.party.common.ui.theme.WHITE

@Composable
fun PartyRecruitmentEditHelpCard(
    onCloseHelpCard: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        Box(
            modifier = Modifier
                .offset(x = 166.dp)
                .width(10.dp)
                .height(5.dp)
                .background(color = GRAY500, shape = TriangleEdge()),
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
            ,
            shape = RoundedCornerShape(LARGE_CORNER_SIZE),
            colors = CardDefaults.cardColors(
                containerColor = GRAY500,
            ),
            border = BorderStroke(1.dp, GRAY500),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 8.dp, top = 8.dp, bottom = 8.dp),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    TextComponent(
                        text = "모집 최대 인원에 대해 알려드릴게요",
                        fontSize = T3,
                        textColor = WHITE,
                        fontWeight = FontWeight.Bold,
                    )
                    DrawableIconButton(
                        icon = painterResource(id = R.drawable.close2),
                        iconColor = WHITE,
                        iconSize = 16.dp,
                        contentDescription = "close",
                        onClick = onCloseHelpCard
                    )
                }
                HeightSpacer(heightDp = 12.dp)
                TextComponent(
                    text = "파티원을 포함해 16명의 파티원을 모집할 수 있어요",
                    fontSize = B2,
                    textColor = WHITE,
                )
                HeightSpacer(20.dp)
            }
        }
    }
}