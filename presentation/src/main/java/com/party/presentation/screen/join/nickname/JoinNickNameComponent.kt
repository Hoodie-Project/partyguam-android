package com.party.presentation.screen.join.nickname

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.party.common.AnnotatedTextComponent
import com.party.common.TextComponent
import com.party.common.ui.theme.B3
import com.party.common.ui.theme.DARK100
import com.party.common.ui.theme.GRAY200
import com.party.common.ui.theme.GRAY400
import com.party.common.ui.theme.RED
import com.party.presentation.R

@Composable
fun WarningArea(
    context: Context,
    userNickName: String,
    isValid: Boolean,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        TextComponent(
            modifier = Modifier
                .weight(0.8f)
                .padding(start = 6.dp),
            text = setWarningText(userNickName, isValid),
            fontSize = B3,
            textColor = RED,
            textAlign = Alignment.CenterStart,
        )
        AnnotatedTextComponent(
            modifier = Modifier.weight(0.2f),
            annotatedString = makeAnnotatedStringValid(
                text1 = userNickName.length.toString(),
                text2 = context.getString(R.string.join_nickname4),
                textColor = annotatedTextColor(userNickName, isValid)
            ),
            fontSize = B3,
            textColor = GRAY400,
            textAlign = Alignment.CenterEnd,
        )
    }
}

@Composable
fun setWarningText(userNickName: String, isValid: Boolean): String{
    return if(containsSpecialCharacters(userNickName)) stringResource(id = R.string.join_nickname6)
    else if(userNickName.isEmpty()) ""
    else if(!isValid) stringResource(id = R.string.join_nickname5)
    else ""
}

fun setInputFieldBorderColor(text: String): Color {
    return if(text.isEmpty()) GRAY200 else if(text.length <= 15) DARK100 else RED
}

fun validNickNameInputField(text: String): Boolean{
    return text.isNotEmpty() && text.length <= 15 && text.length >= 2
}

fun annotatedTextColor(
    userNickName: String,
    isValid: Boolean,
): Color {
    return if (userNickName.isEmpty()) GRAY400 else if(isValid) GRAY400 else RED
}

fun makeAnnotatedStringValid(
    text1: String,
    text2: String,
    textColor: Color,
): AnnotatedString {
    return buildAnnotatedString {
        withStyle(
            SpanStyle(
                color = textColor,
            )
        ){
            append(text1)
        }
        append("/")
        append(text2)
    }
}

fun containsSpecialCharacters(input: String): Boolean {
    // 특수문자 확인을 위한 정규식
    val regex = Regex("[^a-zA-Z0-9]") // 알파벳과 숫자가 아닌 문자를 찾음
    return regex.containsMatchIn(input)
}