package com.party.presentation.screen.join.component

import androidx.compose.ui.graphics.Color
import com.party.guam.design.DARK100
import com.party.guam.design.GRAY200
import com.party.guam.design.RED

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

fun containsSpecialCharacters(input: String): Boolean {
    // 한글, 알파벳, 숫자를 제외한 특수문자를 확인하기 위한 정규식
    val regex = Regex("[^가-힣a-zA-Z0-9]") // 한글, 알파벳, 숫자가 아닌 문자를 찾음
    return regex.containsMatchIn(input)
}