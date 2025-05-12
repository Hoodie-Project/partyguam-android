package com.party.common.component.button

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.party.common.utils.WidthSpacer
import com.party.common.ui.theme.B2
import com.party.common.ui.theme.PRIMARY
import com.party.common.ui.theme.WHITE

@Composable
fun ResetAndApplyButtonArea(
    onReset: () -> Unit,
    onApply: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
    ) {
        CustomButton(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
            onClick = onReset,
            buttonText = "초기화",
            textWeight = FontWeight.Bold,
            textSize = B2,
            containerColor = WHITE,
            borderColor = PRIMARY
        )
        WidthSpacer(widthDp = 8.dp)
        CustomButton(
            modifier = Modifier
                .weight(2f)
                .fillMaxHeight(),
            onClick = onApply,
            buttonText = "적용하기",
            textWeight = FontWeight.Bold,
            textSize = B2,
            containerColor = PRIMARY,
            borderColor = PRIMARY
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ResetAndApplyButtonAreaPreview() {
    ResetAndApplyButtonArea(
        onReset = {},
        onApply = {}
    )
}