package com.party.common.component.dialog

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.party.common.HeightSpacer
import com.party.common.component.dialog.component.DialogButton
import com.party.common.component.dialog.component.DialogDescription
import com.party.common.component.dialog.component.DialogTitle
import com.party.common.ui.theme.GRAY400
import com.party.common.ui.theme.LARGE_CORNER_SIZE
import com.party.common.ui.theme.PRIMARY
import com.party.common.ui.theme.WHITE

@Composable
fun OneButtonDialog(
    dialogTitle: String,
    description: String,
    buttonText: String,
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
            border = BorderStroke(1.dp, GRAY400)
        ) {
            Column(
                modifier = Modifier
                    .wrapContentHeight(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                HeightSpacer(heightDp = 32.dp)
                DialogTitle(
                    dialogTitle = dialogTitle
                )
                DialogDescription(
                    modifier = Modifier
                        .padding(horizontal = 16.dp),
                    description = description
                )
                HeightSpacer(heightDp = 32.dp)
                DialogButton(
                    modifier = Modifier
                        .fillMaxWidth(),
                    buttonText = buttonText,
                    containerColor = PRIMARY,
                    onClick = onConfirm
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun OneButtonDialogPreview() {
    OneButtonDialog(
        dialogTitle = "지원자를 수락했어요",
        description = "지원자가 합류를 결정하면\n파티 활동을 시작할 수 있어요.",
        buttonText = "탈퇴",
        onCancel = {},
        onConfirm = {},
    )
}

@Preview(showBackground = true)
@Composable
private fun OneButtonDialogPreview2() {
    OneButtonDialog(
        dialogTitle = "지원자를 거절했어요",
        description = "이 지원자는 파티에 참여할 수 없어요.",
        buttonText = "탈퇴",
        onCancel = {},
        onConfirm = {},
    )
}