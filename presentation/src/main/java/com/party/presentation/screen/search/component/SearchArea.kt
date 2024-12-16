package com.party.presentation.screen.search.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.party.common.R
import com.party.common.component.icon.DrawableIconButton
import com.party.common.component.input_field.InputField
import com.party.common.component.scaffold.ScaffoldCenterBar
import com.party.common.ui.theme.BLACK
import com.party.common.ui.theme.GRAY400
import com.party.common.ui.theme.GRAY500
import com.party.common.ui.theme.PRIMARY

@Composable
fun SearchArea(
    keyword: String,
    onValueChange: (String) -> Unit,
    onNavigationClick: () -> Unit,
    searchAction: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        ScaffoldCenterBar(
            navigationIcon = {
                DrawableIconButton(
                    icon = painterResource(id = R.drawable.arrow_back),
                    iconColor = BLACK,
                    iconSize = 24.dp,
                    contentDescription = "back",
                    onClick = { onNavigationClick() }
                )
            },
            title = {
                InputField(
                    inputText = keyword,
                    placeHolder = "파티, 모집공고 이름을 검색해보세요.",
                    placeHolderText = GRAY500,
                    inputTextColor = BLACK,
                    onValueChange = onValueChange,
                    elevation = 0.dp,
                    borderCornerSize = 0.dp,
                    borderColor = Color.Unspecified,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Search,
                        keyboardType = KeyboardType.Text,
                        capitalization = KeyboardCapitalization.Words,
                        autoCorrectEnabled = true
                    ),
                    keyboardActions = KeyboardActions(
                        onSearch = { searchAction() }
                    ),
                )
            },
            actionIcons = {
                DrawableIconButton(
                    icon = painterResource(id = R.drawable.search_icon),
                    iconColor = BLACK,
                    iconSize = 24.dp,
                    contentDescription = "search",
                    onClick = { searchAction() }
                )
            }
        )
        HorizontalDivider(color = PRIMARY)
    }
}