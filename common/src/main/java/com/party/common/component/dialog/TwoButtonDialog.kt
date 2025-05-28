package com.party.common.component.dialog

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.party.common.utils.HeightSpacer
import com.party.common.component.dialog.component.DialogButton
import com.party.common.component.dialog.component.DialogDescription
import com.party.common.component.dialog.component.DialogTitle
import com.party.guam.design.LARGE_CORNER_SIZE
import com.party.guam.design.LIGHT400
import com.party.guam.design.PRIMARY
import com.party.guam.design.WHITE

@Composable
fun TwoButtonDialog(
    dialogTitle: String,
    description: String,
    cancelButtonText: String,
    confirmButtonText: String,
    onCancel: () -> Unit,
    onConfirm: () -> Unit,
) {
    Dialog(
        onDismissRequest = {
            onCancel()
        },
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
        ),
    ) {
        Card(
            modifier = Modifier
                .width(312.dp)
                .wrapContentHeight(),
            shape = RoundedCornerShape(LARGE_CORNER_SIZE),
            colors = CardDefaults.cardColors(
                containerColor = WHITE
            ),
        ) {
            Column(
                modifier = Modifier
                    .wrapContentHeight(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(
                    modifier = Modifier
                        .wrapContentHeight(),
                ) {
                    HeightSpacer(heightDp = 32.dp)
                    DialogTitle(
                        dialogTitle = dialogTitle
                    )
                    DialogDescription(
                        description = description
                    )
                }

                //HeightSpacer(heightDp = 32.dp)
                DialogButtonArea(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 32.dp),
                    cancelButtonText = cancelButtonText,
                    confirmButtonText = confirmButtonText,
                    onCancel = onCancel,
                    onConfirm = onConfirm
                )
            }
        }
    }
}

@Composable
private fun DialogButtonArea(
    modifier: Modifier,
    cancelButtonText: String,
    confirmButtonText: String,
    onCancel: () -> Unit,
    onConfirm: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(44.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        DialogButton(
            modifier = Modifier.weight(0.5f),
            buttonText = cancelButtonText,
            containerColor = LIGHT400,
            onClick = onCancel
        )
        DialogButton(
            modifier = Modifier.weight(0.5f),
            buttonText = confirmButtonText,
            containerColor = PRIMARY,
            onClick = onConfirm
        )
    }
}

@Preview
@Composable
private fun TwoButtonDialogPreview(
    modifier: Modifier = Modifier
) {
    TwoButtonDialog(
        dialogTitle = "나가기",
        description = "입력한 내용들이 모두 초기화굅니다.\n나가시겠습니까?\n" +
                "나가시겠습니까?\n" +
                "나가시겠습니까?",
        cancelButtonText = "취소",
        confirmButtonText = "나가기",
        onCancel = {},
        onConfirm = {}
    )
}

@Preview
@Composable
private fun TwoButtonDialogPreview2(
    modifier: Modifier = Modifier
) {
    TwoButtonDialog(
        dialogTitle = "나가기",
        description = "해당 포지션으로 변경하시나요?",
        cancelButtonText = "취소",
        confirmButtonText = "나가기",
        onCancel = {},
        onConfirm = {}
    )
}