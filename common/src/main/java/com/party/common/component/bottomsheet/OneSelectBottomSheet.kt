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
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberStandardBottomSheetState
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
import com.party.common.utils.WidthSpacer
import com.party.common.component.bottomsheet.component.ApplyButton
import com.party.common.component.bottomsheet.component.BottomSheetTitleArea
import com.party.common.component.bottomsheet.list.partyTypeList
import com.party.common.utils.noRippleClickable
import com.party.common.ui.theme.B1

/*
    단일 선택 바텀시트
*/

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OneSelectBottomSheet(
    bottomSheetTitle: String,
    contentList: List<Pair<String, Int>>,
    selectedText: String,
    onBottomSheetClose: () -> Unit,
    onApply: (String) -> Unit,
) {
    val sheetState = rememberStandardBottomSheetState(
        initialValue = SheetValue.Expanded,
        skipHiddenState = false
    )

    var selectedItem by remember { mutableStateOf(selectedText) }

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
                titleText = bottomSheetTitle,
                onSheetClose = onBottomSheetClose
            )

            PartyTypeBottomSheetContent(
                contentList = contentList,
                selectedPartyType = selectedItem,
                onClick = {
                    selectedItem = it
                }
            )

            ApplyButton(
                buttonText = "선택 완료",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                isActive = true,
                onClick = {onApply(selectedItem)}
            )
        }
    }
}

@Composable
private fun PartyTypeBottomSheetContent(
    contentList: List<Pair<String, Int>>,
    selectedPartyType: String,
    onClick: (String) -> Unit
){
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .height(260.dp)
    ) {
        itemsIndexed(
            items = contentList,
            key = { index, _ ->
                index
            }
        ) { _, item ->
            PartyTypeBottomSheetContentItem(
                text = item.first,
                selectedPartyType = selectedPartyType,
                onClick = onClick,
            )
        }
    }
}

@Composable
private fun PartyTypeBottomSheetContentItem(
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
            painter = if(selectedPartyType == text) painterResource(id = R.drawable.icon_checed) else painterResource(id = R.drawable.icon_uncheckd),
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
    OneSelectBottomSheet(
        bottomSheetTitle = "파티 유형",
        contentList = partyTypeList,
        selectedText = "스터디",
        onBottomSheetClose = {},
        onApply = {}
    )
}