package com.party.common.component.bottomsheet

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.party.common.R
import com.party.common.WidthSpacer
import com.party.common.component.bottomsheet.component.ApplyButton
import com.party.common.component.bottomsheet.component.BottomSheetTitleArea
import com.party.common.noRippleClickable
import com.party.common.ui.theme.B1


// 파티 타입 단일 선택
private val partyTypeList = listOf(
    "미정",
    "스터디",
    "포트폴리오",
    "해커톤",
    "공모전",
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PartyTypeOneSelectBottomSheet(
    selectedPartyType: String,
    onBottomSheetClose: () -> Unit,
    onApply: (String) -> Unit,
) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    var selectedText by remember { mutableStateOf(selectedPartyType) }

    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = {
            onBottomSheetClose()
        },
        containerColor = White,
        dragHandle = null,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(horizontal = 20.dp)
        ) {
            BottomSheetTitleArea(
                titleText = "파티 유형",
                onModelClose = onBottomSheetClose
            )

            PartyTypeBottomSheetContent(
                selectedPartyType = selectedText,
                onClick = {
                    selectedText = it
                }
            )

            ApplyButton(
                buttonText = "선택 완료",
                modifier = Modifier
                    .fillMaxWidth(),
                onClick = {onApply(selectedText)}
            )
        }
    }
}

@Composable
private fun PartyTypeBottomSheetContent(
    selectedPartyType: String,
    onClick: (String) -> Unit
){
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .height(260.dp)
    ) {
        itemsIndexed(
            items = partyTypeList,
            key = { index, _ ->
                index
            }
        ) { _, item ->
            PartyTypeBottomSheetContentItem(
                text = item,
                selectedPartyType = selectedPartyType,
                onClick = onClick,
            )
        }
    }
}

@Composable
fun PartyTypeBottomSheetContentItem(
    text: String,
    selectedPartyType: String,
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
            painter = if(selectedPartyType == text) painterResource(id = R.drawable.checed_icon) else painterResource(id = R.drawable.uncheckd_icon),
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

@Preview(showBackground = true)
@Composable
fun PartyTypeBottomSheetPreview() {
    PartyTypeOneSelectBottomSheet(
        selectedPartyType = "스터디",
        onBottomSheetClose = {},
        onApply = {}
    )
}