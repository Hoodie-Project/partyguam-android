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
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.party.common.utils.HeightSpacer
import com.party.common.R
import com.party.common.component.button.CustomButton
import com.party.common.utils.TextComponent
import com.party.common.utils.WidthSpacer
import com.party.common.component.icon.DrawableIcon
import com.party.common.utils.noRippleClickable
import com.party.common.ui.theme.B1
import com.party.common.ui.theme.B2
import com.party.common.ui.theme.B3
import com.party.common.ui.theme.BLACK
import com.party.common.ui.theme.GRAY100
import com.party.common.ui.theme.GRAY200
import com.party.common.ui.theme.GRAY500
import com.party.common.ui.theme.GRAY600
import com.party.common.ui.theme.LARGE_CORNER_SIZE
import com.party.common.ui.theme.WHITE
import com.party.common.utils.convertIsoToCustomDateFormat

@Composable
fun RecruitmentListItem6(
    date: String,
    status: String,
    statusColor: Color,
    profileImage: String?,
    nickName: String,
    message: String,
    onAccept: () -> Unit,
    onRefusal: () -> Unit,
) {
    var isContentVisible by remember { mutableStateOf(true) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        shape = RoundedCornerShape(LARGE_CORNER_SIZE),
        border = BorderStroke(1.dp, GRAY100),
        colors =
        CardDefaults.cardColors(
            containerColor = WHITE,
        ),
        elevation = CardDefaults.cardElevation(2.dp),
    ) {
        Column(
            modifier = Modifier
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
            ProfileImageAndNickNameArea(
                imageUrl = profileImage,
                nickName = nickName,
            )

            // 애니메이션 영역
            AnimatedVisibility(visible = isContentVisible) {
                Column {
                    HeightSpacer(heightDp = 20.dp)
                    // 지원사유
                    TextComponent(
                        text = message,
                        fontSize = B2,
                        textColor = GRAY600,
                    )
                    HeightSpacer(heightDp = 12.dp)

                    if(status == "검토중"){
                        CancelAndApplyButtonArea(
                            onAccept = onAccept,
                            onRefusal = onRefusal,
                        )
                    }
                }
            }

            HeightSpacer(heightDp = 20.dp)
            HorizontalDivider(color = GRAY200)

            ChangeApplicationFormVisible(
                isContentVisible = isContentVisible,
                onToggle = { isContentVisible = !isContentVisible },
            )
        }
    }
}

// 거절하기, 수락하기 버튼 - 응답대기인 경우 보이기 (마감인경우 버튼 안보임)
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
            textSize = B3
        )
        WidthSpacer(widthDp = 8.dp)
        CustomButton(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
            buttonText = "수락하기",
            textWeight = FontWeight.SemiBold,
            onClick = onAccept,
            textSize = B3,
        )
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
private fun ProfileImageAndNickNameArea(
    imageUrl: String?,
    nickName: String,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        ProfileImageArea(
            imageUrl = imageUrl,
        )
        WidthSpacer(widthDp = 12.dp)
        TextComponent(
            text = nickName,
            fontSize = B1,
        )
    }
}

@Composable
private fun ProfileImageArea(imageUrl: String? = null) {
    ImageLoading(
        modifier = Modifier
            .size(40.dp),
        imageUrl = imageUrl,
        roundedCornerShape = LARGE_CORNER_SIZE,
    )
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
            icon = painterResource(id = if (isContentVisible) R.drawable.icon_arrow_up else R.drawable.icon_arrow_down),
            contentDescription = "",
            iconSize = 16.dp,
            tintColor = GRAY500,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun RecruitmentListItem6Preview() {
    RecruitmentListItem6(
        date = "2024-12-05T08:09:19.765Z",
        status = "검토중",
        statusColor = BLACK,
        profileImage = null,
        nickName = "김철수",
        message = "안녕하세요",
        onAccept = {},
        onRefusal = {}
    )
}