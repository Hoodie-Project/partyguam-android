package com.party.common.component.dialog

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.party.common.HeightSpacer
import com.party.common.R
import com.party.common.component.dialog.component.DialogButton
import com.party.common.component.dialog.component.DialogDescription
import com.party.common.component.dialog.component.DialogTitle
import com.party.common.ui.theme.GRAY400
import com.party.common.ui.theme.LARGE_CORNER_SIZE
import com.party.common.ui.theme.LIGHT400
import com.party.common.ui.theme.PRIMARY
import com.party.common.ui.theme.WHITE

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
                .height(205.dp),
            shape = RoundedCornerShape(LARGE_CORNER_SIZE),
            colors = CardDefaults.cardColors(
                containerColor = WHITE
            ),
            border = BorderStroke(1.dp, GRAY400)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                HeightSpacer(heightDp = 32.dp)
                DialogTitle(
                    dialogTitle = dialogTitle
                )
                DialogDescription(
                    description = description
                )
                HeightSpacer(heightDp = 34.dp)
                DialogButtonArea(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally),
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
            .height(48.dp),
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
        description = "입력한 내용들이 모두 초기화굅니다.\n나가시겠습니까?",
        cancelButtonText = "취소",
        confirmButtonText = "나가기",
        onCancel = {},
        onConfirm = {}
    )
}