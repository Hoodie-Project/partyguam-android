package com.party.presentation.screen.party_user_manage.component

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.party.common.R
import com.party.common.component.icon.DrawableIcon
import com.party.common.component.icon.DrawableIconButton
import com.party.common.component.input_field.InputField
import com.party.common.ui.theme.GRAY100
import com.party.common.ui.theme.GRAY400

@Composable
fun PartyUserSearchArea(
    inputText: String,
    placeHolder: String,
    onValueChange: (String) -> Unit,
) {
    InputField(
        inputText = inputText,
        placeHolder = placeHolder,
        containerColor = GRAY100,
        //inputTextColor = inputTextColor,
        borderColor = GRAY100,
        borderCornerSize = 8.dp,
        elevation = 0.dp,
        onValueChange = onValueChange,
        leadingIcon = {
            DrawableIcon(
                icon = painterResource(id = R.drawable.search_icon),
                contentDescription = "search",
                modifier = Modifier.padding(end = 8.dp),
                tintColor = GRAY400,
            )
        },
        icon = {
            DrawableIconButton(
                icon = painterResource(id = R.drawable.close),
                contentDescription = "clear",
                onClick = {}
            )
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun PartyUserSearchAreaPreview() {
    PartyUserSearchArea(
        inputText = "",
        placeHolder = "닉네임을 검색해 보세요.",
        onValueChange = {}
    )
}