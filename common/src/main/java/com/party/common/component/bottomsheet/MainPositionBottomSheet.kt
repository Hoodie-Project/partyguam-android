package com.party.common.component.bottomsheet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.party.common.TextComponent
import com.party.common.component.bottomsheet.component.BottomSheetButtonArea
import com.party.common.component.bottomsheet.component.BottomSheetTitleArea
import com.party.common.ui.theme.B1
import com.party.common.ui.theme.GRAY100

val mainPositionList = listOf(
    "전체",
    "기획자",
    "디자이너",
    "개발자",
    "마케터/광고"
)

/*
    메인 포지션 리스트 바텀 시트 - 한개만 선택 가능
*/

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainPositionBottomSheet(
    selectedPosition: String,
    onBottomSheetClose: () -> Unit,
    onPositionClick: (String) -> Unit,
    onReset: () -> Unit,
    onApply: () -> Unit,
) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
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
                .height(400.dp)
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                BottomSheetTitleArea(
                    titleText = "모집공고",
                    onSheetClose = onBottomSheetClose
                )

                MainPositionBottomSheetContent(
                    selectedPosition = selectedPosition,
                    onClick = onPositionClick
                )
            }

            BottomSheetButtonArea(
                onReset = onReset,
                onApply = onApply,
            )
        }
    }
}

@Composable
private fun MainPositionBottomSheetContent(
    selectedPosition: String,
    onClick: (String) -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .height(260.dp)
    ) {
        itemsIndexed(
            items = mainPositionList,
            key = { index, _ ->
                index
            }
        ){_, item ->
            MainPositionBottomSheetContentItem(
                mainPosition = item,
                fontWeight = if(selectedPosition == item) FontWeight.SemiBold else FontWeight.Normal,
                containerColor = if(selectedPosition == item) GRAY100 else White,
                onClick = onClick
            )
        }
    }
}

@Composable
private fun MainPositionBottomSheetContentItem(
    mainPosition: String,
    fontWeight: FontWeight,
    containerColor: Color,
    onClick: (String) -> Unit,
) {
    TextComponent(
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp)
            .background(containerColor),
        text = mainPosition,
        fontSize = B1,
        fontWeight = fontWeight,
        textAlign = Alignment.Center,
        onClick = { onClick(mainPosition) }
    )
}