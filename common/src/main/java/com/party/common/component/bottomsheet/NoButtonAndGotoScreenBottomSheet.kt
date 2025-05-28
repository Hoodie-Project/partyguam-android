package com.party.common.component.bottomsheet

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.party.common.utils.TextComponent
import com.party.common.component.bottomsheet.component.BottomSheetTitleArea
import com.party.guam.design.B1
import com.party.guam.design.BLACK
import com.party.guam.design.RED

val partyMemberManageList = listOf("파티장 위임", "포지션 변경", "내보내기")
val partyMasterManageList = listOf("포지션 변경")

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoButtonAndGotoScreenBottomSheet(
    bottomSheetTitle: String,
    contentList: List<String>,
    onBottomSheetClose: () -> Unit,
    onClick: (String) -> Unit,
) {
    val sheetState = rememberStandardBottomSheetState(
        initialValue = SheetValue.Expanded,
        skipHiddenState = false
    )

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

            ContentArea(
                contentList = contentList,
                onClick = onClick
            )
        }
    }
}

@Composable
private fun ContentArea(
    contentList: List<String>,
    onClick: (String) -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        itemsIndexed(
            items = contentList,
            key = { index, _ ->
                index
            }
        ) { _, item ->
            TextComponent(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                text = item,
                fontSize = B1,
                align = Alignment.Center,
                textColor = if(item == partyMemberManageList[2]) RED else BLACK,
                onClick = { onClick(item) }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun NoButtonAndGotoScreenBottomSheetPreview() {
    NoButtonAndGotoScreenBottomSheet(
        bottomSheetTitle = "파티원 관리",
        contentList = partyMemberManageList,
        onBottomSheetClose = {},
        onClick = {}
    )
}