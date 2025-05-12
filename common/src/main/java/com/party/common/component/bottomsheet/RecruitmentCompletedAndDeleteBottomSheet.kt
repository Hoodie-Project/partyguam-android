package com.party.common.component.bottomsheet

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.party.common.utils.TextComponent
import com.party.common.ui.theme.B1

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecruitmentCompletedAndDeleteBottomSheet(
    onModelClose: () -> Unit,
    text: String,
    status: String,
    textColor: Color,
    onPreview: () -> Unit,
    onPartyRecruitmentCompleted: () -> Unit,
    onPartyRecruitmentDeleted: () -> Unit,
) {
    val sheetState = rememberStandardBottomSheetState(
        initialValue = SheetValue.Expanded,
        skipHiddenState = false
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
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "close",
                    modifier = Modifier
                        .padding(end = 16.dp)
                        .size(24.dp)
                        .clickable { onModelClose() }
                )
            }

            TextComponent(
                text = "미리보기",
                align = Alignment.Center,
                fontSize = B1,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                onClick = onPreview
            )

            TextComponent(
                text = text,
                align = Alignment.Center,
                textColor = textColor,
                fontSize = B1,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                onClick = {
                    if(status == "completed"){
                        onPartyRecruitmentDeleted()
                    }else {
                        onPartyRecruitmentCompleted()
                    }
                }
            )
        }
    }
}

@Preview
@Composable
private fun RecruitmentCompletedAndDeleteBottomSheetPreview(){
    RecruitmentCompletedAndDeleteBottomSheet(
        onModelClose = {},
        status = "completed",
        textColor = Color.Red,
        text = "삭제하기",
        onPreview = {},
        onPartyRecruitmentDeleted = {},
        onPartyRecruitmentCompleted = {},
    )
}