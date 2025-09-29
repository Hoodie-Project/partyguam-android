package com.party.common.utils

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.party.common.R
import com.party.common.component.icon.DrawableIcon
import com.party.guam.design.B3
import com.party.guam.design.BLACK
import com.party.guam.design.GRAY200
import com.party.guam.design.GRAY400
import com.party.guam.design.PRIMARY
import com.party.guam.design.T2
import com.party.guam.design.T3
import com.tmfrl.compose.loading.DotRippleSpinner

@Composable
fun AnnotatedTextComponent(
    modifier: Modifier = Modifier,
    annotatedString: AnnotatedString,
    textColor: Color = Color.Black,
    textAlign: Alignment = Alignment.CenterStart,
    fontWeight: FontWeight = FontWeight.Normal,
    fontSize: TextUnit,
    textDecoration: TextDecoration? = null,
    onClick: () -> Unit = {}
) {
    Box(
        modifier = modifier.noRippleClickable { onClick() },
        contentAlignment = textAlign,
    ){
        Text(
            text = annotatedString,
            color = textColor,
            fontWeight = fontWeight,
            fontSize = fontSize,
            textDecoration = textDecoration,
        )
    }
}

@Composable
fun TextComponent(
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Start,
    text: String,
    textColor: Color = Color.Black,
    align: Alignment = Alignment.CenterStart,
    fontWeight: FontWeight = FontWeight.Normal,
    fontSize: TextUnit,
    textDecoration: TextDecoration? = null,
    overflow: TextOverflow = TextOverflow.Visible,
    maxLines: Int = Int.MAX_VALUE,
    letterSpacing: TextUnit = 0.sp,
    onClick: () -> Unit = {},
) {
    Box(
        modifier = modifier.noRippleClickable { onClick() },
        contentAlignment = align,
    ){
        Text(
            text = text,
            color = textColor,
            textAlign = textAlign,
            fontWeight = fontWeight,
            fontSize = fontSize,
            textDecoration = textDecoration,
            overflow = overflow,
            maxLines = maxLines,
            letterSpacing = letterSpacing,
        )
    }
}

@Composable
fun WidthSpacer(
    widthDp: Dp,
) {
    Spacer(modifier = Modifier.width(widthDp))
}

@Composable
fun HeightSpacer(
    heightDp: Dp,
) {
    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(heightDp)
    )
}

@SuppressLint("ModifierFactoryUnreferencedReceiver")
inline fun Modifier.noRippleClickable(crossinline onClick: () -> Unit): Modifier = composed {
    clickable(
        indication = null,
        interactionSource = remember { MutableInteractionSource() }
    ) {
        onClick()
    }
}

@Composable
fun ScreenExplainArea(
    mainExplain: String,
    subExplain: String,
) {
    HeightSpacer(heightDp = 32.dp)

    TextComponent(
        text = mainExplain,
        fontWeight = FontWeight.Bold,
        fontSize = T2,
    )

    HeightSpacer(heightDp = 12.dp)

    TextComponent(
        text = subExplain,
        fontSize = T3,
    )
}

@Composable
fun LoadingProgressBar() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        DotRippleSpinner()
    }
}



// 단계 상태 정의
enum class StepStatus {
    COMPLETED,  // 완료됨 (체크 아이콘)
    CURRENT,    // 현재 단계 (번호 원형)
    PENDING     // 대기 중 (비활성화)
}

// 단계 정보 데이터 클래스
data class StepInfo(
    val number: String,
    val title: String,
    val status: StepStatus
)

@Composable
fun ProfileIndicatorSection(
    steps: List<StepInfo>
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        steps.forEachIndexed { index, step ->
            IndicatorComponent(
                number = step.number,
                title = step.title,
                status = step.status
            )

            // 마지막 단계가 아니면 구분선 추가
            if (index < steps.size - 1) {
                HorizontalDivider(
                    modifier = Modifier
                        .weight(1f) // 남은 공간을 모두 차지
                        .height(2.dp)
                        .padding(horizontal = 8.dp), // 좌우 8dp 여백
                    color = if (step.status == StepStatus.COMPLETED) PRIMARY else GRAY200,
                )
            }
        }
    }
}


@Composable
fun IndicatorComponent(
    number: String,
    title: String,
    status: StepStatus,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        when (status) {
            StepStatus.COMPLETED -> {
                DrawableIcon(
                    icon = painterResource(R.drawable.icon_check_primary),
                    contentDescription = "",
                    modifier = Modifier.size(24.dp),
                    tintColor = Color.Unspecified
                )
            }
            StepStatus.CURRENT -> {
                IndicatorCircle(
                    text = number,
                    container = PRIMARY,
                    textColor = BLACK
                )
            }
            StepStatus.PENDING -> {
                IndicatorCircle(
                    text = number,
                    container = GRAY200,
                    textColor = GRAY400
                )
            }
        }

        WidthSpacer(widthDp = 6.dp)

        val textColor = when (status) {
            StepStatus.COMPLETED, StepStatus.CURRENT -> BLACK
            StepStatus.PENDING -> GRAY400
        }

        TextComponent(
            text = title,
            fontSize = B3,
            fontWeight = FontWeight.Bold,
            textColor = textColor
        )
    }
}

@Composable
fun IndicatorCircle(
    text: String,
    container: Color,
    textColor: Color = BLACK,
) {
    Card(
        modifier = Modifier.size(24.dp),
        shape = CircleShape,
        colors = CardDefaults.cardColors(containerColor = container),
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ){
            Text(
                text = text,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = textColor,
            )
        }
    }
}