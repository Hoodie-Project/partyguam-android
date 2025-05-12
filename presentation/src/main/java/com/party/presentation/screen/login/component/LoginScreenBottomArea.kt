package com.party.presentation.screen.login.component

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.party.common.utils.HeightSpacer
import com.party.common.utils.noRippleClickable
import com.party.common.ui.theme.GRAY500
import com.party.common.ui.theme.GRAY600

@Composable
fun LoginScreenBottomArea(
    onGotoInquire: () -> Unit,
    onTermsClick: () -> Unit,
    onPrivacyClick: () -> Unit,
) {
    var termsStart = 0
    var termsEnd = 0
    var privacyStart = 0
    var privacyEnd = 0

    val annotatedText = buildAnnotatedString {
        append("소셜 로그인 가입 시 ")

        // "이용약관" 밑줄 및 클릭 범위 설정
        termsStart = length
        pushStyle(SpanStyle(textDecoration = TextDecoration.Underline, color = Color.Black))
        append("이용약관")
        pop()
        termsEnd = length

        append(" ")

        // "개인정보처리방침" 밑줄 및 클릭 범위 설정
        privacyStart = length
        pushStyle(SpanStyle(textDecoration = TextDecoration.Underline, color = Color.Black))
        append("개인정보처리방침")
        pop()
        privacyEnd = length

        append(" 에 동의한 것으로 간주합니다.")
    }

    var textLayoutResult by remember { mutableStateOf<TextLayoutResult?>(null) }

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = annotatedText,
            modifier = Modifier
                .fillMaxWidth()
                .pointerInput(Unit) {
                    detectTapGestures { offsetPosition ->
                        textLayoutResult?.let { layoutResult ->
                            val offset = layoutResult.getOffsetForPosition(offsetPosition)
                            when {
                                offset in termsStart until termsEnd -> onTermsClick()
                                offset in privacyStart until privacyEnd -> onPrivacyClick()
                            }
                        }
                    }
                },
            style = TextStyle(color = GRAY600, fontSize = 13.sp),
            onTextLayout = { layoutResult ->
                textLayoutResult = layoutResult // TextLayoutResult 저장
            }
        )

        HeightSpacer(heightDp = 40.dp)

        Text(
            text = "문의하기",
            color = GRAY500,
            fontSize = 14.sp,
            textDecoration = TextDecoration.Underline,
            modifier = Modifier
                .noRippleClickable { onGotoInquire() }
        )
    }
}