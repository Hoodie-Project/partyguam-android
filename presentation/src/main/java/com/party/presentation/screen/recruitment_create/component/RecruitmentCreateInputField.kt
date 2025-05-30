package com.party.presentation.screen.recruitment_create.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.party.common.component.input_field.InputField
import com.party.guam.design.BLACK
import com.party.guam.design.GRAY200
import com.party.guam.design.GRAY400
import com.party.guam.design.PRIMARY

@Composable
fun RecruitmentCreateInputField(
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