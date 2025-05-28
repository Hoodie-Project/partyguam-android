package com.party.common.component.bottomsheet

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.party.common.utils.HeightSpacer
import com.party.common.utils.TextComponent
import com.party.common.component.bottomsheet.component.BottomSheetTitleArea
import com.party.guam.design.B1

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationDeleteBottomSheet(
    onBottomSheetClose: () -> Unit,
    onDeleteNotification: () -> Unit,
){
    val sheetState = rememberStandardBottomSheetState(
        initialValue = SheetValue.Expanded,
        skipHiddenState = false
    )

    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = onBottomSheetClose,
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
                titleText = "",
                onSheetClose = onBottomSheetClose
            )

            TextComponent(
                text = "삭제하기",
                fontSize = B1,
                align = Alignment.Center,
                fontWeight = FontWeight.Medium,
                onClick = onDeleteNotification,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
            )
            HeightSpacer(heightDp = 34.dp)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NotificationDeleteBottomSheetPreview() {
    NotificationDeleteBottomSheet(
        onDeleteNotification = {},
        onBottomSheetClose = {}
    )
}