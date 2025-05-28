package com.party.presentation.screen.party_create.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.party.common.R
import com.party.common.component.input_field.InputField
import com.party.common.component.icon.DrawableIconButton
import com.party.guam.design.B3
import com.party.guam.design.BLACK
import com.party.guam.design.GRAY200
import com.party.guam.design.GRAY400
import com.party.guam.design.PRIMARY

@Composable
fun PartyCreateInputField(
    inputText: String,
    placeHolder: String,
    readOnly: Boolean,
    icon: @Composable () -> Unit,
    onValueChange: (String) -> Unit,
) {
    val inputTextColor = if(inputText.isEmpty()) GRAY400 else BLACK
    val borderColor = if(inputText.isEmpty()) GRAY200 else PRIMARY
    val elevation = if(inputText.isEmpty()) 2.dp else 0.dp

    InputField(
        inputText = inputText,
        placeHolder = placeHolder,
        inputTextColor = inputTextColor,
        borderColor = borderColor,
        elevation = elevation,
        readOnly = readOnly,
        icon = { icon() },
        onValueChange = onValueChange
    )
}

@Composable
fun PartyCreateValidField(
    count: Int
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End
    ){
        Text(text = "$count", color = GRAY400, fontSize = B3)
        Text(text = "/", color = GRAY400, fontSize = B3)
        Text(text = "15", color = GRAY400, fontSize = B3)
    }
}

@Preview(showBackground = true)
@Composable
private fun PartyCreateInputFieldPreview() {
    PartyCreateInputField(
        inputText = "",
        placeHolder = "15자 이내로 입력해주세요",
        readOnly = false,
        icon = {
            DrawableIconButton(
                icon = painterResource(id = R.drawable.icon_close),
                contentDescription = "",
                onClick = {}
            )
        },
        onValueChange = {}
    )
}