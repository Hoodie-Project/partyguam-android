package com.party.presentation.screen.party_edit.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.party.common.WidthSpacer
import com.party.common.component.button.CustomButton
import com.party.common.ui.theme.DARK100
import com.party.common.ui.theme.GRAY200
import com.party.common.ui.theme.LIGHT300
import com.party.common.ui.theme.WHITE

@Composable
fun SelectPartyStateButtonArea(
    onProgress: () -> Unit,
    onFinish: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp)
    ) {
        CustomButton(
            modifier = Modifier.weight(1f),
            onClick = onProgress,
            buttonText = "진행중",
            containerColor = LIGHT300,
            borderColor = DARK100,
        )
        WidthSpacer(widthDp = 12.dp)
        CustomButton(
            modifier = Modifier.weight(1f),
            onClick = onFinish,
            buttonText = "종료",
            containerColor = WHITE,
            borderColor = GRAY200,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SelectPartyStateButtonAreaPreview() {
    SelectPartyStateButtonArea(
        onProgress = {},
        onFinish = {},
    )
}