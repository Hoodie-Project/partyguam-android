package com.party.presentation.screen.party_edit.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.party.common.component.input_field.InputField
import com.party.common.ui.theme.B3
import com.party.common.ui.theme.BLACK
import com.party.common.ui.theme.GRAY200
import com.party.common.ui.theme.GRAY400
import com.party.common.ui.theme.PRIMARY

@Composable
fun PartyEditInputField(
    inputText: String,
    placeHolder: String,
    readOnly: Boolean,
    icon: @Composable () -> Unit,
    onValueChange: (String) -> Unit,
) {
    val inputTextColor = if(inputText.isEmpty()) GRAY400 else BLACK
    val borderColor = if(inputText.isEmpty()) GRAY200 else PRIMARY
    val elevation = if(inputText.isEmpty()) 4.dp else 0.dp

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
fun PartyEditValidField(
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
private fun PartyEditInputFieldPreview() {
    PartyEditInputField(
        inputText = "inputText",
        placeHolder = "placeHolder",
        readOnly = false,
        icon = { },
        onValueChange = { }
    )
}