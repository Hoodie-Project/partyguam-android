package com.party.common.component.bottomsheet.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.party.common.utils.WidthSpacer
import com.party.guam.design.B2
import com.party.guam.design.BLACK
import com.party.guam.design.GRAY400
import com.party.guam.design.LARGE_CORNER_SIZE
import com.party.guam.design.LIGHT200
import com.party.guam.design.LIGHT400
import com.party.guam.design.PRIMARY
import com.party.guam.design.WHITE

@Composable
fun BottomSheetButtonArea(
    isActiveResetButton: Boolean,
    isActiveApplyButton: Boolean,
    onReset: () -> Unit,
    onApply: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(72.dp)
            .padding(vertical = 12.dp, horizontal = 20.dp)
    ) {
        ResetButton(
            modifier = Modifier
                .weight(1f),
            isActive = isActiveResetButton,
            onClick = onReset,
        )
        WidthSpacer(widthDp = 8.dp)
        ApplyButton(
            modifier = Modifier
                .weight(2f),
            isActive = isActiveApplyButton,
            onClick = onApply,
        )
    }
}

@Composable
fun ResetButton(
    modifier: Modifier,
    isActive: Boolean,
    onClick: () -> Unit,
) {
    val borderColor = if(isActive) PRIMARY else LIGHT200
    val containerColor = WHITE
    val textColor = if(isActive) BLACK else GRAY400

    Button(
        onClick = { onClick() },
        modifier = modifier
            .height(56.dp),
        shape = RoundedCornerShape(LARGE_CORNER_SIZE),
        border = BorderStroke(1.dp, borderColor),
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
        ),
    ) {
        Text(
            text = "초기화",
            fontWeight = FontWeight.Bold,
            fontSize = B2,
            color = textColor,
        )
    }
}

@Composable
fun ApplyButton(
    buttonText: String = "적용하기",
    modifier: Modifier,
    isActive: Boolean,
    onClick: () -> Unit,
) {
    val borderColor = if(isActive) PRIMARY else LIGHT200
    val containerColor = if(isActive) PRIMARY else LIGHT400
    val textColor = if(isActive) BLACK else GRAY400

    Button(
        onClick = { onClick() },
        modifier = modifier
            .height(56.dp),
        shape = RoundedCornerShape(LARGE_CORNER_SIZE),
        border = BorderStroke(1.dp, borderColor),
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
        ),
    ) {
        Text(
            text = buttonText,
            fontWeight = FontWeight.Bold,
            fontSize = B2,
            color = textColor,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun BottomSheetButtonAreaPreview() {
    BottomSheetButtonArea(
        isActiveResetButton = false,
        isActiveApplyButton = true,
        onReset = {},
        onApply = {},
    )
}