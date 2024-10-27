package com.party.presentation.screen.home.tab_recruitment

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.party.common.R
import com.party.common.TextComponent
import com.party.common.WidthSpacer
import com.party.common.noRippleClickable
import com.party.common.ui.theme.B1
import com.party.common.ui.theme.B2
import com.party.common.ui.theme.BLACK
import com.party.common.ui.theme.LARGE_CORNER_SIZE
import com.party.common.ui.theme.PRIMARY
import com.party.common.ui.theme.T2
import com.party.common.ui.theme.WHITE

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PartyTypeModal(
    titleText: String,
    selectedPartyType: MutableList<String>,
    onClick: (String) -> Unit,
    onModelClose: () -> Unit,
    onReset: () -> Unit,
    onApply: () -> Unit,
) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = {
            onModelClose()
        },
        containerColor = Color.White,
        dragHandle = null,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(20.dp)
        ) {
            ModalTitleArea(
                titleText = titleText,
                onModelClose = onModelClose,
            )
            ModelCheckListArea(
                selectedPartyType = selectedPartyType,
                onClick = onClick,
            )
            ModalBottomArea(
                buttonText1 = stringResource(id = R.string.recruitment_modal1),
                buttonText2 = stringResource(id = R.string.recruitment_modal2),
                onReset = onReset,
                onApply = onApply,
            )
        }
    }
}

@Composable
private fun ModalTitleArea(
    titleText: String,
    onModelClose: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        TextComponent(
            modifier = Modifier.align(Alignment.Center),
            text = titleText,
            fontSize = T2,
            fontWeight = FontWeight.SemiBold,
            textColor = BLACK,
            textAlign = Alignment.Center,
        )
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { onModelClose() }
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "close",
                )
            }
        }
    }
}

@Composable
private fun ModelCheckListArea(
    selectedPartyType: MutableList<String>,
    onClick: (String) -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        itemsIndexed(
            items = partyTypeList,
            key = { index, _ ->
                index
            }
        ) { _, item ->
            ModalCheckListItem(
                text = item,
                selectedPartyType = selectedPartyType,
                onClick = {onClick(it)}
            )
        }
    }
}

@Composable
private fun ModalCheckListItem(
    text: String,
    selectedPartyType: MutableList<String>,
    onClick: (String) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp)
            .noRippleClickable {
                onClick(text)
            },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            modifier = Modifier.size(20.dp),
            painter = if(selectedPartyType.contains(text)) painterResource(id = R.drawable.checed_icon) else painterResource(id = R.drawable.uncheckd_icon),
            contentDescription = "check",
        )

        WidthSpacer(widthDp = 6.dp)
        Text(
            text = text,
            fontWeight = if(selectedPartyType.contains(text)) FontWeight.Bold else FontWeight.Normal,
            fontSize = B1,
        )
    }
}

@Composable
private fun ModalBottomArea(
    buttonText1: String,
    buttonText2: String,
    onReset: () -> Unit,
    onApply: () -> Unit,
) {
    Row(
        modifier = Modifier
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