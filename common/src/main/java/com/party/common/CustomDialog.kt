package com.party.common

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.party.common.ui.theme.BLACK
import com.party.common.ui.theme.GRAY400
import com.party.common.ui.theme.LARGE_CORNER_SIZE
import com.party.common.ui.theme.LIGHT400
import com.party.common.ui.theme.PRIMARY
import com.party.common.ui.theme.WHITE

@Composable
fun WarningDialog(
    onClose: (Boolean) -> Unit
) {
    Dialog(
        onDismissRequest = {
            onClose(false)
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
                    .fillMaxSize()
            ) {
                HeightSpacer(heightDp = 32.dp)
                TextArea()
                HeightSpacer(heightDp = 32.dp)
                ButtonArea(
                    onClose = onClose
                )
            }
        }
    }
}

@Composable
fun TextArea() {
    TextComponent(
        modifier = Modifier.fillMaxWidth(),
        text = stringResource(id = R.string.dialog1),
        fontSize = 18.sp,
        textAlign = Alignment.Center,
        fontWeight = FontWeight.Bold,
    )

    HeightSpacer(heightDp = 24.dp)

    TextComponent(
        modifier = Modifier.fillMaxWidth(),
        text = stringResource(id = R.string.dialog2),
        fontSize = 16.sp,
        textAlign = Alignment.Center
    )
}

@Composable
fun ButtonArea(
    onClose: (Boolean) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        DialogButton(
            modifier = Modifier.weight(0.5f),
            text = stringResource(id = R.string.dialog3),
            containerColor = LIGHT400,
            onClose = onClose
        )
        DialogButton(
            modifier = Modifier.weight(0.5f),
            text = stringResource(id = R.string.dialog1),
            containerColor = PRIMARY
        )
    }
}

@Composable
fun DialogButton(
    modifier: Modifier,
    text: String,
    containerColor: Color,
    onClose: (Boolean) -> Unit = {}
) {
    Button(
        modifier = modifier,
        onClick = {
            onClose(false)
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = BLACK
        ),
        shape = RectangleShape
    ) {
        Text(
            text = text,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
        )
    }
}

@Preview
@Composable
fun testDialogPreview(
    modifier: Modifier = Modifier
) {
    WarningDialog(
        onClose = {}
    )
}