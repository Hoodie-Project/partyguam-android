package com.party.presentation.screen.reports.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.party.common.TextComponent
import com.party.common.component.input_field.MultiLineInputField
import com.party.common.ui.theme.BLACK
import com.party.common.ui.theme.T2

@Composable
fun ReportsTitleArea() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        TextComponent(
            text = "신고 사유를 작성해 주세요.",
            fontSize = T2,
            fontWeight = FontWeight.Bold,
            textColor = BLACK,
        )
    }
}

@Composable
fun ReportsInputReasonArea(
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