package com.party.presentation.screen.auth_setting.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.party.common.TextComponent
import com.party.common.ui.theme.T3

@Composable
fun TermsArea() {
    Column {
        TermsAreaItem(
            text = "서비스 소개"
        )
        TermsAreaItem(
            text = "고객문의"
        )
        TermsAreaItem(
            text = "이용약관"
        )
        TermsAreaItem(
            text = "개인정보 처리방침"
        )
    }
}

@Composable
private fun TermsAreaItem(
    text: String,
) {
    TextComponent(
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp)
            .padding(start = 20.dp),
        text = text,
        fontSize = T3,
        fontWeight = FontWeight.SemiBold
    )
}

@Preview(showBackground = true)
@Composable
private fun TermsAreaPreview() {
    TermsArea()
}