package com.party.presentation.screen.party_apply.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.party.common.HeightSpacer
import com.party.common.TextComponent
import com.party.common.component.input_field.MultiLineInputField
import com.party.common.ui.theme.B2
import com.party.common.ui.theme.BLACK
import com.party.common.ui.theme.T2

@Composable
fun PartyApplyTitleArea() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        TextComponent(
            text = "지원 사유",
            fontSize = T2,
            fontWeight = FontWeight.Bold,
            textColor = BLACK,
        )
        HeightSpacer(heightDp = 4.dp)
        TextComponent(
            text = "250자 이내로 자유롭게 작성해 주세요",
            fontSize = B2,
            textColor = BLACK,
        )
    }
}

@Composable
fun PartyApplyInputReasonArea(
    inputText: String,
    placeHolder: String,
    onValueChange: (String) -> Unit,
    onAllDeleteInputText: () -> Unit,
) {
    MultiLineInputField(
        placeHolder = placeHolder,
        inputText = inputText,
        onValueChange = onValueChange,
        onAllDeleteInputText = onAllDeleteInputText
    )
}

@Preview(showBackground = true)
@Composable
private fun PartyApplyTitleAreaPreview() {
    PartyApplyTitleArea()
}

@Preview(showBackground = true)
@Composable
private fun PartyApplyInputReasonAreaPreview(
    modifier: Modifier = Modifier
) {
    PartyApplyInputReasonArea(
        inputText = "",
        placeHolder = "지원 사유를 입력해주세요",
        onValueChange = {},
        onAllDeleteInputText = {}
    )
}