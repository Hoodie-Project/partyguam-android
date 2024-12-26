package com.party.presentation.screen.profile_edit.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.party.common.HeightSpacer
import com.party.common.R
import com.party.common.TextComponent
import com.party.common.component.icon.DrawableIcon
import com.party.common.noRippleClickable
import com.party.common.ui.theme.GRAY500
import com.party.common.ui.theme.MEDIUM_PADDING_SIZE
import com.party.common.ui.theme.PRIMARY
import com.party.common.ui.theme.T2

val editList = listOf(
    "경력/포지션",
    "관심 지역",
    "희망 시간",
    "성향",
    "이력서 및 포트폴리오 링크"
)

@Composable
fun EditArea(
    count: Int,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(MEDIUM_PADDING_SIZE)
    ) {
        HeightSpacer(heightDp = 32.dp)
        EditAreaItem(
            text = editList[0],
            onClick = {},
        )
        HeightSpacer(heightDp = 60.dp)
        EditAreaItem(
            text = editList[1],
            onClick = {},
        )
        HeightSpacer(heightDp = 60.dp)
        EditAreaItem(
            text = editList[2],
            onClick = {},
        )
        HeightSpacer(heightDp = 60.dp)
        EditAreaItem(
            text = editList[3],
            onClick = {},
        )
        HeightSpacer(heightDp = 60.dp)
        EditAreaItem(
            text = editList[4],
            onClick = {},
        )
        HeightSpacer(heightDp = 60.dp)

        // 참여파티목록
        val text = buildAnnotatedString {
            append("참여 파티 목록 ")
            withStyle(
                SpanStyle(
                    color = PRIMARY
                )
            ){
                append("$count")
            }
            append("건")
        }
        Text(
            text = text,
            fontSize = T2,
            fontWeight = FontWeight.SemiBold
        )

        HeightSpacer(heightDp = 60.dp)
    }
}

@Composable
private fun EditAreaItem(
    text: String,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(25.dp)
            .noRippleClickable { onClick() },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        TextComponent(
            text = text,
            fontSize = T2,
            fontWeight = FontWeight.SemiBold,
        )

        DrawableIcon(
            icon = painterResource(id = R.drawable.icon_arrow_right),
            contentDescription = "",
            tintColor = GRAY500,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun EditAreaPreview() {
    EditArea(
        count = 5
    )
}
