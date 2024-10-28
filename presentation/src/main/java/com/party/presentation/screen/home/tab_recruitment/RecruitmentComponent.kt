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
import com.party.common.TextComponent
import com.party.common.WidthSpacer
import com.party.common.ui.theme.B2
import com.party.common.ui.theme.BLACK
import com.party.common.ui.theme.LARGE_CORNER_SIZE
import com.party.common.ui.theme.PRIMARY
import com.party.common.ui.theme.T2
import com.party.common.ui.theme.WHITE

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
            textAlign = Alignment.Center,
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
    buttonText2: String,
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
            buttonContainerColor = WHITE,
            buttonBorderColor = PRIMARY,
            onClick = onReset,
        )
        WidthSpacer(widthDp = 8.dp)
        ModalBottomAreaItem(
            modifier = Modifier.weight(2f),
            buttonText = buttonText2,
            buttonContainerColor = PRIMARY,
            buttonBorderColor = PRIMARY,
            onClick = onApply,
        )
    }
}

@Composable
fun ModalBottomAreaItem(
    modifier: Modifier,
    buttonText: String,
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
            fontSize = B2,
            color = BLACK,
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
fun ModalCheckListItemPreview(modifier: Modifier = Modifier) {
    ModalCheckListItem(
        text = "전체",
        selectedPartyType = mutableListOf(),
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
        buttonText2 = "적용하기",
        onReset = {},
        onApply = {},
    )
}

val partyTypeList = listOf(
    "전체",
    "스터디",
    "포트폴리오",
    "해커톤",
    "공모전",
)