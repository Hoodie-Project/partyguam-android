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
    return when {
        text.isEmpty() -> GRAY200
        validNickNameInputField(text) -> DARK100
        else -> RED
    }
}

fun validNickNameInputField(text: String): Boolean {
    if (text.isEmpty() || text.length < 2 || text.length > 15) return false

    // 온전한 한글 음절 (가~힣), 영문 대소문자만 허용
    val regex = Regex("^[a-zA-Z가-힣]+$")

    if (!regex.matches(text)) return false

    // 자음/모음만 입력된 경우 걸러내기 (단일 글자 중 'ㄱ'-'ㅎ', 'ㅏ'-'ㅣ' 포함 여부 검사)
    val invalidJamo = Regex("[ㄱ-ㅎㅏ-ㅣ]")
    return !invalidJamo.containsMatchIn(text)
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