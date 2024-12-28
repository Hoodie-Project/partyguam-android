package com.party.presentation.screen.profile_edit_portfolio.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.party.common.HeightSpacer
import com.party.common.R
import com.party.common.TextComponent
import com.party.common.component.icon.DrawableIconButton
import com.party.common.component.input_field.InputField
import com.party.common.ui.theme.GRAY200
import com.party.common.ui.theme.GRAY400
import com.party.common.ui.theme.PRIMARY
import com.party.common.ui.theme.T2

@Composable
fun InputLinkAndTitleArea(
    modifier: Modifier,
    linkText: String,
    onLinkValueChange: (String) -> Unit,
    titleText: String,
    onTitleValueChange: (String) -> Unit,
    onDeleteLink: () -> Unit,
    onDeleteTitle: () -> Unit,
) {
    Column(
        modifier = modifier
    ) {
        InputLinkArea(
            inputText = linkText,
            onValueChange = onLinkValueChange,
            onDelete = onDeleteLink
        )
        HeightSpacer(heightDp = 60.dp)

        InputLinkTitleArea(
            inputText = titleText,
            onValueChange = onTitleValueChange,
            onDelete = onDeleteTitle
        )
    }
}

@Composable
private fun InputLinkArea(
    inputText: String,
    onValueChange: (String) -> Unit,
    onDelete: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        TextComponent(
            text = "링크",
            fontSize = T2,
            fontWeight = FontWeight.SemiBold,
        )
        HeightSpacer(heightDp = 12.dp)

        InputField(
            inputText = inputText,
            placeHolder = "링크를 입력해 주세요.",
            onValueChange = onValueChange,
            borderColor = if(inputText.isNotEmpty()) PRIMARY else GRAY200,
            icon = {
                if(inputText.isNotEmpty()){
                    DrawableIconButton(
                        icon = painterResource(id = R.drawable.icon_close),
                        contentDescription = "close",
                        onClick = onDelete
                    )
                }
            }
        )
    }
}

@Composable
private fun InputLinkTitleArea(
    inputText: String,
    onValueChange: (String) -> Unit,
    onDelete: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        TextComponent(
            text = "링크 제목",
            fontSize = T2,
            fontWeight = FontWeight.SemiBold,
        )
        HeightSpacer(heightDp = 12.dp)

        InputField(
            inputText = inputText,
            placeHolder = "링크 제목을 입력해 주세요. (최대 15글자)",
            onValueChange = { if(it.length <= 15){ onValueChange(it) } },
            borderColor = if(inputText.isNotEmpty()) PRIMARY else GRAY200,
            icon = {
                if(inputText.isNotEmpty()){
                    DrawableIconButton(
                        icon = painterResource(id = R.drawable.icon_close),
                        contentDescription = "close",
                        onClick = onDelete
                    )
                }
            }
        )
        HeightSpacer(heightDp = 12.dp)

        Text(
            modifier = Modifier
                .fillMaxWidth(),
            textAlign = TextAlign.End,
            text = "${inputText.length} / 15",
            color = GRAY400
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun InputLinkAndTitleAreaPreview() {
    InputLinkAndTitleArea(
        modifier = Modifier,
        linkText = "https://www.google.com",
        onLinkValueChange = {},
        titleText = "GoogleGoogle",
        onTitleValueChange = {},
        onDeleteLink = {},
        onDeleteTitle = {}
    )
}