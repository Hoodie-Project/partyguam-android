package com.party.presentation.screen.home.tab_recruitment

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.party.common.utils.TextComponent
import com.party.common.utils.WidthSpacer
import com.party.common.utils.fs
import com.party.guam.design.B2
import com.party.guam.design.BLACK
import com.party.guam.design.LARGE_CORNER_SIZE
import com.party.guam.design.PRIMARY
import com.party.guam.design.T2

@Composable
fun ModalTitleArea(
    titleText: String,
    onModelClose: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
    ) {
        TextComponent(
            modifier = Modifier.align(Alignment.Center),
            text = titleText,
            fontSize = T2,
            fontWeight = FontWeight.SemiBold,
            textColor = BLACK,
            align = Alignment.Center,
        )

        Icon(
            imageVector = Icons.Default.Close,
            contentDescription = "close",
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 16.dp)
                .size(24.dp)
                .clickable { onModelClose() }
        )
    }
}

@Composable
fun ModalBottomArea(
    modifier: Modifier = Modifier,
    buttonText1: String,
    buttonTextColor1: Color,
    buttonContainerColor1: Color,
    buttonBorderColor1: Color,
    buttonText2: String,
    buttonTextColor2: Color,
    buttonContainerColor2: Color,
    buttonBorderColor2: Color,
    onReset: () -> Unit,
    onApply: () -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(72.dp)
            .padding(top = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        ModalBottomAreaItem(
            modifier = Modifier.weight(1f),
            buttonText = buttonText1,
            buttonTextColor = buttonTextColor1,
            buttonContainerColor = buttonContainerColor1,
            buttonBorderColor = buttonBorderColor1,
            onClick = onReset,
        )
        WidthSpacer(widthDp = 8.dp)
        ModalBottomAreaItem(
            modifier = Modifier.weight(2f),
            buttonText = buttonText2,
            buttonTextColor = buttonTextColor2,
            buttonContainerColor = buttonContainerColor2,
            buttonBorderColor = buttonBorderColor2,
            onClick = onApply,
        )
    }
}

@Composable
fun ModalBottomAreaItem(
    modifier: Modifier,
    buttonText: String,
    buttonTextColor: Color = BLACK,
    buttonContainerColor: Color,
    buttonBorderColor: Color,
    onClick: () -> Unit,
) {
    Button(
        onClick = { onClick() },
        modifier = modifier
            .height(56.dp),
        shape = RoundedCornerShape(LARGE_CORNER_SIZE),
        border = BorderStroke(1.dp, buttonBorderColor),
        colors = ButtonDefaults.buttonColors(
            containerColor = buttonContainerColor,
        ),
    ) {
        Text(
            text = buttonText,
            fontWeight = FontWeight.Bold,
            fontSize = fs(B2),
            color = buttonTextColor,
        )
    }
}


@Preview
@Composable
fun ModalTitleAreaPreview(
    modifier: Modifier = Modifier
) {
    ModalTitleArea(
        titleText = "파티 유형",
        onModelClose = {},
    )
}

@Preview
@Composable
fun ModalCheckListItemPreview(
    modifier: Modifier = Modifier
) {
    ModalCheckListItem(
        text = Pair("전체", 0),
        selectedPartyType = emptyList(),
        onClick = {}
    )
}

@Preview
@Composable
fun ModalBottomAreaPreview(
    modifier: Modifier = Modifier
) {
    ModalBottomArea(
        buttonText1 = "초기화",
        buttonTextColor1 = BLACK,
        buttonContainerColor1 = PRIMARY,
        buttonBorderColor1 = PRIMARY,
        buttonText2 = "적용하기",
        buttonTextColor2 = BLACK,
        buttonContainerColor2 = PRIMARY,
        buttonBorderColor2 = PRIMARY,
        onReset = {},
        onApply = {},
    )
}