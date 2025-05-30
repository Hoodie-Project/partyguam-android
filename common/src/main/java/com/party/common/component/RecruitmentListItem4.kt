package com.party.common.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.party.common.utils.HeightSpacer
import com.party.common.R
import com.party.common.utils.TextComponent
import com.party.common.utils.WidthSpacer
import com.party.guam.design.B2
import com.party.guam.design.B3
import com.party.guam.design.GRAY100
import com.party.guam.design.GRAY500
import com.party.guam.design.LARGE_CORNER_SIZE
import com.party.guam.design.PRIMARY
import com.party.guam.design.RED
import com.party.guam.design.T3
import com.party.guam.design.WHITE
import com.party.common.utils.convertIsoToCustomDateFormat

@Composable
fun RecruitmentListItem4(
    status: String,
    createdAt: String,
    main: String,
    sub: String,
    recruitedCount: Int,
    recruitingCount: Int,
    applicationCount: Int,
    onClick: () -> Unit,
    onMoreClick: () -> Unit,
    icon: @Composable (onClick: () -> Unit) -> Unit = {},
) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(127.dp),
    ){
        Card(
            onClick = onClick,
            modifier = Modifier
                .fillMaxSize(),
            colors = CardDefaults.cardColors(containerColor = WHITE),
            shape = RoundedCornerShape(LARGE_CORNER_SIZE),
            border = BorderStroke(1.dp, GRAY100),
            elevation = CardDefaults.cardElevation(2.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp, vertical = 16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextComponent(
                        text = "모집일 ${convertIsoToCustomDateFormat(createdAt)}",
                        fontSize = B3,
                        textColor = GRAY500
                    )

                    icon {
                        onMoreClick()
                    }
                }

                HeightSpacer(heightDp = 8.dp)
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    TextComponent(
                        text = main,
                        fontSize = T3,
                        fontWeight = FontWeight.SemiBold,
                    )
                    WidthSpacer(widthDp = 8.dp)
                    Image(
                        painter = painterResource(id = R.drawable.vertical_rectangle),
                        contentDescription = "",
                        modifier = Modifier
                            .width(2.dp)
                            .height(12.dp),
                    )
                    WidthSpacer(widthDp = 8.dp)
                    TextComponent(
                        text = sub,
                        fontSize = T3,
                        fontWeight = FontWeight.SemiBold,
                    )
                }

                HeightSpacer(heightDp = 20.dp)

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(20.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    if (status == "completed") {
                        TextComponent(
                            text = "모집마감",
                            fontSize = B2,
                        )
                    } else {
                        RecruitingCountArea(
                            text = "모집중",
                            number = "$recruitedCount / $recruitingCount",
                            textColor = RED
                        )
                        WidthSpacer(widthDp = 24.dp)
                        RecruitingCountArea(
                            text = "지원자",
                            number = "$applicationCount",
                            textColor = PRIMARY
                        )
                    }
                }
            }
        }

    }
}

@Composable
private fun RecruitingCountArea(
    text: String,
    number: String,
    textColor: Color,
) {
    Row(
        modifier = Modifier
            .fillMaxHeight(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        TextComponent(
            text = text,
            fontSize = T3,
            modifier = Modifier.alignByBaseline() // Baseline에 맞춤
        )
        WidthSpacer(widthDp = 4.dp)
        TextComponent(
            text = number,
            fontSize = T3,
            textColor = textColor,
            modifier = Modifier.alignByBaseline() // Baseline에 맞춤
        )
    }
}


@Preview(showBackground = true)
@Composable
private fun RecruitmentListItem4Preview() {
    RecruitmentListItem4(
        createdAt = "2024-06-05T15:30:45.123Z",
        main = "개발자",
        sub = "안드로이드",
        recruitedCount = 2,
        recruitingCount = 3,
        applicationCount = 1,
        status = "active",
        onClick = {},
        onMoreClick = {},
    )
}

@Preview(showBackground = true)
@Composable
private fun RecruitmentListItem4Preview1() {
    RecruitmentListItem4(
        createdAt = "2024-06-05T15:30:45.123Z",
        main = "개발자",
        sub = "안드로이드",
        recruitedCount = 2,
        recruitingCount = 3,
        applicationCount = 1,
        status = "completed",
        onClick = {},
        onMoreClick = {},
    )
}