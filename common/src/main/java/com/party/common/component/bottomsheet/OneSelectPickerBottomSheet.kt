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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.party.common.utils.TextComponent
import com.party.common.component.bottomsheet.component.ApplyButton
import com.party.common.component.bottomsheet.component.BottomSheetTitleArea
import com.party.common.ui.theme.B2
import com.party.common.ui.theme.BLACK
import com.party.common.ui.theme.GRAY500
import com.party.common.ui.theme.T2

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OneSelectPickerBottomSheet(
    bottomSheetTitle: String,
    selectedText: String,
    peopleCountList: List<String>,
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

            OneSelectPickerContent(
                selectedText = selectedItem,
                peopleCountList = peopleCountList,
                onClick = {
                    selectedItem = it
                }
            )

            ApplyButton(
                buttonText = "선택 완료",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                onClick = {onApply(selectedItem)}
            )
        }
    }
}

@Composable
private fun OneSelectPickerContent(
    selectedText: String,
    peopleCountList: List<String>,
    onClick: (String) -> Unit
){
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .height(156.dp)
    ) {
        itemsIndexed(
            items = peopleCountList,
            key = { index, _ ->
                index
            }
        ) { _, item ->
            OneSelectPickerContentItem(
                text = item,
                fontSize = if(selectedText == item) T2 else B2,
                textColor = if(selectedText == item) BLACK else GRAY500,
                onClick = { onClick(it) }
            )
        }
    }
}

@Composable
private fun OneSelectPickerContentItem(
    text: String,
    fontSize: TextUnit,
    textColor: Color,
    onClick: (String) -> Unit,
) {
    TextComponent(
        onClick = { onClick(text) },
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp),
        text = text,
        fontSize = fontSize,
        align = Alignment.Center,
        textColor = textColor,
    )
}


@Preview(showBackground = true)
@Composable
private fun OneSelectPickerBottomSheetPreview() {
    OneSelectPickerBottomSheet(
        bottomSheetTitle = "모집 인원",
        selectedText = "2명",
        peopleCountList = listOf("1명", "2명", "3명", "4명", "5명"),
        onBottomSheetClose = {},
        onApply = {}
    )
}