package com.party.presentation.screen.recover_auth.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.party.common.utils.HeightSpacer
import com.party.common.utils.TextComponent
import com.party.guam.design.BLACK
import com.party.guam.design.PRIMARY
import com.party.guam.design.T1
import com.party.guam.design.T3

@Composable
fun RecoverDescriptionArea() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        RecoverTitle()
        HeightSpacer(heightDp = 20.dp)

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(78.dp)
        ) {
            RecoverDescription1(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(44.dp)
            )
            HeightSpacer(heightDp = 12.dp)
            RecoverDescription2(
                modifier = Modifier
                    .height(22.dp)
            )
        }

    }
}

@Composable
private fun RecoverTitle() {
    TextComponent(
        text = "최근 탈퇴한 계정이 있습니다.",
        fontSize = T1,
        fontWeight = FontWeight.Bold,
    )
}

@Composable
private fun RecoverDescription1(
    modifier: Modifier
) {
    TextComponent(
        modifier = modifier,
        text = "탈퇴 후 30일 동안 정보가 보관되며. 해당 기간 내 정보를 복구할 수 있어요.",
        fontSize = T3,
    )
}

@Composable
private fun RecoverDescription2(
    modifier: Modifier
) {
    Box(
        modifier = modifier
    ){
        Text(
            text = buildAnnotatedString {
                append("복구를 원하시면, ")
                withStyle(style = SpanStyle(color = PRIMARY, fontWeight = FontWeight.Bold)) {
                    append("계정 복구")
                }
                append(" 버튼을 눌러주세요!")
            },
            fontSize = T3,
            color = BLACK
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun RecoverDescriptionAreaPreview() {
    RecoverDescriptionArea()
}