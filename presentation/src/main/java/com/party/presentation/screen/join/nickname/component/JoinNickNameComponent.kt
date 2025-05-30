package com.party.presentation.screen.join.nickname.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import com.party.common.R
import com.party.guam.design.DARK100
import com.party.guam.design.GRAY200
import com.party.guam.design.GRAY400
import com.party.guam.design.RED


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
    // 한글, 알파벳, 숫자를 제외한 특수문자를 확인하기 위한 정규식
    val regex = Regex("[^가-힣a-zA-Z0-9]") // 한글, 알파벳, 숫자가 아닌 문자를 찾음
    return regex.containsMatchIn(input)
}