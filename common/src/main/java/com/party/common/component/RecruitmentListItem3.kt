package com.party.common.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.party.common.HeightSpacer
import com.party.common.R
import com.party.common.TextComponent
import com.party.common.WidthSpacer
import com.party.common.component.button.CustomButton
import com.party.common.component.chip.Chip
import com.party.common.component.icon.DrawableIcon
import com.party.common.convertIsoToCustomDateFormat
import com.party.common.noRippleClickable
import com.party.common.ui.theme.B2
import com.party.common.ui.theme.B3
import com.party.common.ui.theme.GRAY100
import com.party.common.ui.theme.GRAY200
import com.party.common.ui.theme.GRAY500
import com.party.common.ui.theme.GRAY600
import com.party.common.ui.theme.LARGE_CORNER_SIZE
import com.party.common.ui.theme.T3
import com.party.common.ui.theme.TYPE_COLOR_BACKGROUND
import com.party.common.ui.theme.TYPE_COLOR_TEXT
import com.party.common.ui.theme.WHITE
import com.party.common.ui.theme.YELLOW

@Composable
fun RecruitmentListItem3(
    date: String,
    status: String,
    statusColor: Color,
    partyType: String,
    title: String,
    main: String,
    sub: String,
    content: String,
    onClick: () -> Unit,
    onRefusal: () -> Unit,
    onAccept: () -> Unit,
) {
    var isContentVisible by remember { mutableStateOf(true) }

    Card(
        onClick = { onClick() },
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        shape = RoundedCornerShape(LARGE_CORNER_SIZE),
        border = BorderStroke(1.dp, GRAY100),
        colors =
            CardDefaults.cardColors(
                containerColor = WHITE,
            ),
        elevation = CardDefaults.cardElevation(4.dp),
    ) {
        Column(
            modifier =
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(top = 16.dp),
        ) {
            RecruitmentDataAndState(
                applyDate = date,
                status = status,
                statusColor = statusColor
            )
            HeightSpacer(heightDp = 12.dp)
            RecruitmentInfoArea(
                partyType = partyType,
                title = title,
                main = main,
                sub = sub,
            )

            // 애니메이션 영역
            AnimatedVisibility(visible = isContentVisible) {
                Column {
                    HeightSpacer(heightDp = 20.dp)
                    RecruitmentContent(content = content)
                    HeightSpacer(heightDp = 12.dp)

                    when(status){
                        "응답대기" -> {
                            CancelAndApplyButtonArea(
                                onRefusal = onRefusal,
                                onAccept = onAccept,
                            )
                        }
                        "검토중" -> {
                            CancelButton(
                                onCancel = {

                                }
                            )
                        }
                    }

                }
            }

            HeightSpacer(heightDp = 20.dp)
            HorizontalDivider(
                color = GRAY200,
            )

            ChangeApplicationFormVisible(
                isContentVisible = isContentVisible,
                onToggle = { isContentVisible = !isContentVisible },
            )
        }
    }
}

@Composable
private fun RecruitmentDataAndState(
    applyDate: String,
    status: String,
    statusColor: Color,
) {
    Row(
        modifier =
        Modifier
            .fillMaxWidth()
            .height(17.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        TextComponent(
            text = "지원일 ${convertIsoToCustomDateFormat(applyDate)}",
            fontSize = B3,
            textColor = GRAY500,
        )

        TextComponent(
            text = status,
            fontSize = B3,
            textColor = statusColor,
        )
    }
}

@Composable
private fun RecruitmentInfoArea(
    imageUrl: String? = null,
    partyType: String,
    title: String,
    main: String,
    sub: String,
) {
    Row(
        modifier =
        Modifier
            .fillMaxWidth()
            .height(90.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        RecruitmentImageArea(
            imageUrl = imageUrl,
        )
        WidthSpacer(widthDp = 12.dp)
        RecruitmentContent(
            partyType = partyType,
            title = title,
            main = main,
            sub = sub,
        )
    }
}

@Composable
private fun RecruitmentImageArea(imageUrl: String? = null) {
    ImageLoading(
        modifier =
        Modifier
            .width(120.dp)
            .height(90.dp),
        imageUrl = imageUrl,
        roundedCornerShape = LARGE_CORNER_SIZE,
    )
}

@Composable
private fun RecruitmentContent(
    partyType: String,
    title: String,
    main: String,
    sub: String,
) {
    Column {
        RecruitmentCategory(
            partyType = partyType,
        )
        HeightSpacer(heightDp = 8.dp)
        RecruitmentTitle(
            title = title,
        )
        HeightSpacer(heightDp = 8.dp)

        RecruitmentPositionArea(
            main = main,
            sub = sub,
        )
    }
}

@Composable
private fun RecruitmentCategory(partyType: String) {
    Chip(
        containerColor = TYPE_COLOR_BACKGROUND,
        contentColor = TYPE_COLOR_TEXT,
        text = partyType,
    )
}

@Composable
private fun RecruitmentTitle(title: String) {
    TextComponent(
        text = title,
        fontSize = T3,
        fontWeight = FontWeight.SemiBold,
        overflow = TextOverflow.Ellipsis,
        maxLines = 1,
    )
}

@Composable
private fun RecruitmentPositionArea(
    main: String,
    sub: String,
) {
    TextComponent(
        modifier =
            Modifier
                .fillMaxWidth(),
        text = "$main | $sub",
        fontSize = B2,
    )
}

@Composable
private fun RecruitmentContent(content: String) {
    TextComponent(
        modifier =
            Modifier
                .fillMaxWidth(),
        text = content,
        fontSize = B2,
        textColor = GRAY600,
    )
}

// 지원 취소 - 검토중일 경우 보이기
@Composable
private fun CancelButton(
    onCancel: () -> Unit,
) {
    CustomButton(
        modifier = Modifier
            .fillMaxWidth()
            .height(36.dp),
        buttonText = "지원 취소",
        textWeight = FontWeight.SemiBold,
        containerColor = WHITE,
        onClick = onCancel,
    )
}


// 거절하기, 수락하기 버튼 - 응답대기인 경우 보이기
@Composable
private fun CancelAndApplyButtonArea(
    onRefusal: () -> Unit,
    onAccept: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(36.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        CustomButton(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
            buttonText = "거절하기",
            textWeight = FontWeight.SemiBold,
            containerColor = WHITE,
            onClick = onRefusal,
        )
        WidthSpacer(widthDp = 8.dp)
        CustomButton(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
            buttonText = "수락하기",
            textWeight = FontWeight.SemiBold,
            onClick = onAccept,
        )
    }
}

@Composable
private fun ChangeApplicationFormVisible(
    isContentVisible: Boolean,
    onToggle: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .noRippleClickable { onToggle() },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        TextComponent(
            text = if (isContentVisible) "지원서 닫기" else "지원서 열기",
            fontSize = B3,
            textColor = GRAY600,
            onClick = { onToggle() },
        )
        WidthSpacer(widthDp = 4.dp)
        DrawableIcon(
            icon = painterResource(id = if (isContentVisible) R.drawable.arrow_up_icon else R.drawable.arrow_down_icon),
            contentDescription = "",
            iconSize = 16.dp,
            tintColor = GRAY500,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun RecruitmentListItem3Preview() {
    RecruitmentListItem3(
        date = "2024-12-05T08:09:19.765Z",
        status = "거절",
        statusColor = YELLOW,
        partyType = "스터디",
        title = "스터디 모집합니다",
        main = "대학생",
        sub = "서울",
        content = "저는 개발을 좋아하고 사랑합니다. 그렇기 때문에 저는 파티에 들어가서 잘할 수 있습니다. 잘부탁드립니다. 무조건 수락해주세요.",
        onClick = {},
        onRefusal = {},
        onAccept = {},
    )
}
