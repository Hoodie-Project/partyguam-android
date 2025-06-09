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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.party.common.component.bottomsheet.component.BottomSheetTitleArea
import com.party.common.utils.TextComponent
import com.party.guam.design.B1
import com.party.guam.design.BLACK
import com.party.guam.design.EXTRA_LARGE_BUTTON_HEIGHT2
import com.party.guam.design.RED

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoreBottomSheet(
    isShowPartyExit: Boolean,
    onBottomSheetClose: () -> Unit,
    onReport: () -> Unit,
    onExitParty: () -> Unit,
) {
    val sheetState = rememberStandardBottomSheetState(
        initialValue = SheetValue.Expanded,
        skipHiddenState = false
    )

    var selectContent by remember { mutableStateOf("") }

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
                titleText = "더보기",
                onSheetClose = onBottomSheetClose
            )

            ContentArea(
                isShowPartyExit = isShowPartyExit,
                selectContent = selectContent,
                onReport = {
                    selectContent = it
                    onReport()
                },
                onExitParty = {
                    selectContent = it
                    onExitParty()
                }
            )
        }
    }
}

@Composable
private fun ContentArea(
    isShowPartyExit: Boolean,
    selectContent: String,
    onReport: (String) -> Unit,
    onExitParty: (String) -> Unit,
) {
    TextComponent(
        modifier = Modifier
            .fillMaxWidth()
            .height(EXTRA_LARGE_BUTTON_HEIGHT2),
        text = "신고하기",
        fontSize = B1,
        textColor = if(selectContent == "신고하기") RED else BLACK,
        align = Alignment.Center,
        onClick = { onReport("신고하기") }
    )

    if(isShowPartyExit){
        TextComponent(
            modifier = Modifier
                .fillMaxWidth()
                .height(EXTRA_LARGE_BUTTON_HEIGHT2),
            text = "파티 떠나기",
            fontSize = B1,
            textColor = if(selectContent == "파티 떠나기") RED else BLACK,
            align = Alignment.Center,
            onClick = { onExitParty("파티 떠나기") }
        )
    }

}

@Preview(showBackground = true)
@Composable
private fun MoreBottomSheetPreview() {
    MoreBottomSheet(
        isShowPartyExit = true,
        onBottomSheetClose = {},
        onReport = {},
        onExitParty = {}
    )
}

@Preview(showBackground = true)
@Composable
private fun MoreBottomSheetPreview2() {
    MoreBottomSheet(
        isShowPartyExit = false,
        onBottomSheetClose = {},
        onReport = {},
        onExitParty = {}
    )
}