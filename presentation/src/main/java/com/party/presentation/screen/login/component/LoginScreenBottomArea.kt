package com.party.presentation.screen.login.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.party.common.AnnotatedTextComponent
import com.party.common.HeightSpacer
import com.party.common.R
import com.party.common.TextComponent
import com.party.common.ui.theme.B2
import com.party.common.ui.theme.GRAY500
import com.party.common.ui.theme.GRAY600

@Composable
fun LoginScreenBottomArea(
) {
    AnnotatedTextComponent(
        annotatedString = makeAnnotatedString(
            text1 = stringResource(id = R.string.login5),
            text2 = stringResource(id = R.string.login6),
            text3 = stringResource(id = R.string.login7),
        ),
        textColor = GRAY600,
        fontSize = B2,
    )

    HeightSpacer(heightDp = 40.dp)

    TextComponent(
        modifier = Modifier.fillMaxWidth(),
        text = stringResource(id = R.string.login8),
        textColor = GRAY500,
        align = Alignment.Center,
        fontSize = B2,
        textDecoration = TextDecoration.Underline,
    )
}

fun makeAnnotatedString(
    text1: String,
    text2: String,
    text3: String,
): AnnotatedString {
    return buildAnnotatedString {
        append(text1)
        withStyle(
            SpanStyle(
                color = GRAY600,
                textDecoration = TextDecoration.Underline,
            )
        ){
            append(text2)
        }
        append(text3)
    }
}