package com.party.presentation.screen.search.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.party.common.R
import com.party.common.component.icon.DrawableIconButton
import com.party.common.component.input_field.InputField
import com.party.common.component.scaffold.ScaffoldCenterBar
import com.party.guam.design.BLACK
import com.party.guam.design.GRAY500
import com.party.guam.design.PRIMARY

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
        val keyboardController = LocalSoftwareKeyboardController.current

        ScaffoldCenterBar(
            navigationIcon = {
                DrawableIconButton(
                    icon = painterResource(id = R.drawable.icon_arrow_back),
                    iconColor = BLACK,
                    iconSize = 24.dp,
                    contentDescription = "back",
                    onClick = { onNavigationClick() }
                )
            },
            title = {
                InputField(
                    inputText = keyword,
                    leadingIconAndInputTextDuration = 0.dp,
                    placeHolder = "파티, 모집공고 이름을 검색해보세요.",
                    placeHolderColor = GRAY500,
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
                        onSearch = {
                            keyboardController?.hide()
                            searchAction()
                        }
                    ),
                )
            },
            actionIcons = {
                DrawableIconButton(
                    icon = painterResource(id = R.drawable.icon_search),
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

@Preview(showBackground = true)
@Composable
private fun SearchAreaPreview(){
    SearchArea(
        keyword = "",
        onValueChange = {},
        onNavigationClick = {},
        searchAction = {},
    )
}