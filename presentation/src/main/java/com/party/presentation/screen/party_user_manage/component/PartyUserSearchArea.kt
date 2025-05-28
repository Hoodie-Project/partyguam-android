package com.party.presentation.screen.party_user_manage.component

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.party.common.R
import com.party.common.component.icon.DrawableIcon
import com.party.common.component.icon.DrawableIconButton
import com.party.common.component.input_field.InputField
import com.party.guam.design.GRAY100
import com.party.guam.design.GRAY400
import com.party.guam.design.GRAY500

@Composable
fun PartyUserSearchArea(
    inputText: String,
    placeHolder: String,
    onValueChange: (String) -> Unit,
    onRemoveAll: () -> Unit,
    searchAction: (String) -> Unit,
) {
    InputField(
        inputText = inputText,
        placeHolder = placeHolder,
        placeHolderColor = GRAY500,
        containerColor = GRAY100,
        borderColor = GRAY100,
        borderCornerSize = 8.dp,
        elevation = 0.dp,
        onValueChange = onValueChange,
        leadingIcon = {
            DrawableIcon(
                icon = painterResource(id = R.drawable.icon_search),
                contentDescription = "search",
                modifier = Modifier.padding(end = 8.dp),
                tintColor = GRAY400,
            )
        },
        icon = {
            DrawableIconButton(
                icon = painterResource(id = R.drawable.icon_close),
                contentDescription = "clear",
                onClick = onRemoveAll
            )
        },
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search,
            keyboardType = KeyboardType.Text,
            capitalization = KeyboardCapitalization.Words,
            autoCorrectEnabled = true
        ),
        keyboardActions = KeyboardActions(
            onSearch = { searchAction(inputText) }
        ),
    )
}

@Preview(showBackground = true)
@Composable
private fun PartyUserSearchAreaPreview() {
    PartyUserSearchArea(
        inputText = "",
        placeHolder = "닉네임을 검색해 보세요.",
        onValueChange = {},
        onRemoveAll = {},
        searchAction = {},
    )
}