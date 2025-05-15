package com.party.presentation.screen.recruitment_detail.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.party.common.component.NetworkImageLoad
import com.party.common.component.button.CustomButton
import com.party.common.ui.theme.B1
import com.party.common.ui.theme.B3
import com.party.common.ui.theme.BLACK
import com.party.common.ui.theme.GRAY100
import com.party.common.ui.theme.GRAY500
import com.party.common.ui.theme.GRAY600
import com.party.common.ui.theme.LARGE_CORNER_SIZE
import com.party.common.ui.theme.MEDIUM_PADDING_SIZE
import com.party.common.ui.theme.PRIMARY
import com.party.common.ui.theme.RED
import com.party.common.ui.theme.T2
import com.party.common.utils.HeightSpacer
import com.party.common.utils.TextComponent
import com.party.common.utils.WidthSpacer
import com.party.common.utils.convertIsoToCustomDateFormat
import com.party.common.utils.noRippleClickable
import com.party.presentation.enum.StatusType

@Composable
fun RecruitmentCategory(
    title: String,
    containerColor: Color,
    contentColor: Color
) {
    Card(
        modifier = Modifier
            .wrapContentWidth()
            .height(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = containerColor,
            contentColor = contentColor,
        ),
        shape = RoundedCornerShape(LARGE_CORNER_SIZE),
        elevation = CardDefaults.cardElevation(4.dp),
    ) {
        Box(
            modifier = Modifier
                .wrapContentWidth()
                .fillMaxHeight()
                .padding(horizontal = 8.dp),
            contentAlignment = Alignment.Center
        ){
            Text(
                text = title,
                fontSize = B3,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
fun RecruitmentImageArea(
    imageUrl: String?,
    title: String,
    tag: String,
    type: String,
    onGoToPartyDetail: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
            .padding(horizontal = MEDIUM_PADDING_SIZE),
    ) {
        // 카드 생성 (테두리는 카드 외부에만 적용됨)
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp),
            shape = RoundedCornerShape(16.dp),
            border = BorderStroke(1.dp, GRAY100), // 카드의 테두리
        ) {
            // 카드 내부의 컨텐츠
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                // 이미지 로딩
                NetworkImageLoad(
                    url = imageUrl,
                    modifier = Modifier
                        .fillMaxSize()
                        .noRippleClickable {
                            onGoToPartyDetail()
                        }
                )

                // 하단 그라데이션 오버레이
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp) // 그라데이션 높이
                        .align(Alignment.BottomStart)
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(Color.Transparent, Color.White),
                                startY = 0f,
                                endY = Float.POSITIVE_INFINITY
                            )
                        )
                )

                // 그라데이션 위에 텍스트 배치
                TextComponent(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomStart)
                        .padding(start = 20.dp, bottom = 16.dp),
                    text = title,
                    fontSize = T2,
                    textColor = BLACK,
                    fontWeight = FontWeight.Bold,
                    onClick = onGoToPartyDetail
                )
            }
        }

        RecruitmentTypeArea(
            tag = tag,
            recruitmentType = type
        )
    }
}

@Composable
fun RecruitmentTypeArea(
    tag: String,
    recruitmentType: String,
) {
    Row(
        modifier = Modifier
            .wrapContentHeight()
            .padding(start = 16.dp, top = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ){
        RecruitmentCategory(
            title = StatusType.fromType(tag).toDisplayText(),
            containerColor = Color(0xFFD5F0E3),
            contentColor = Color(0xFF016110)
        )
        
        RecruitmentCategory(
            title = recruitmentType,
            containerColor = Color(0xFFF6F6F6),
            contentColor = Color(0xFF454545)
        )
    }
}

@Composable
fun RecruitmentCurrentInfoArea(
    recruitingCount: String,
    applicationCount: Int,
    createDate: String,
) {
    Column(
        modifier = Modifier
            .padding(horizontal = MEDIUM_PADDING_SIZE),
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        RecruitmentInfoItem(
            title = "모집중",
            content = recruitingCount,
            textColor = RED,
        )
        RecruitmentInfoItem(
            title = "지원자",
            content = applicationCount.toString(),
            textColor = PRIMARY,
        )
        RecruitmentInfoItem(
            title = "모집일",
            content = convertIsoToCustomDateFormat(createDate),
            textColor = BLACK,
        )
    }
}

@Composable
fun RecruitmentInfoItem(
    title: String,
    content: String,
    textColor: Color,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        TextComponent(
            text = title,
            fontSize = B1,
            textColor = GRAY500,
        )
        WidthSpacer(widthDp = 6.dp)
        TextComponent(
            text = content,
            fontSize = B1,
            textColor = textColor,
            fontWeight = FontWeight.SemiBold,
        )
    }
}

// 모집 부분
@Composable
fun RecruitmentPositionAndCountArea(
    position: String,
    recruitingCount: String,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = MEDIUM_PADDING_SIZE),
    ) {
        RecruitmentTitle(
            title = "모집 부분",
        )
        HeightSpacer(heightDp = 20.dp)
        RecruitmentPositionAndCount(
            position = position,
            recruitingCount = recruitingCount
        )
    }
}

@Composable
fun RecruitmentPositionAndCount(
    position: String,
    recruitingCount: String,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        RecruitmentPositionAndCountItem(
            title = "포지션",
            content = position,
        )
        HeightSpacer(heightDp = 12.dp)
        RecruitmentPositionAndCountItem(
            title = "인원",
            content = recruitingCount,
        )
    }
}

@Composable
fun RecruitmentPositionAndCountItem(
    title: String,
    content: String,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        TextComponent(
            modifier = Modifier
                .width(48.dp),
            text = title,
            fontSize = B1,
            textColor = GRAY500,
        )
        WidthSpacer(widthDp = 6.dp)
        TextComponent(
            text = content,
            fontSize = B1,
            textColor = BLACK,
            fontWeight = FontWeight.SemiBold
        )
    }
}

// 모집 공고
@Composable
fun RecruitmentDescription(
    content: String,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = MEDIUM_PADDING_SIZE),
    ) {
        RecruitmentTitle(
            title = "모집 공고",
        )
        HeightSpacer(heightDp = 20.dp)
        RecruitmentDescriptionArea(
            content = content
        )
    }
}

@Composable
fun RecruitmentDescriptionArea(
    content: String,
) {
    TextComponent(
        text = content,
        fontSize = B1,
        textColor = GRAY600,
    )
}

@Composable
fun RecruitmentTitle(
    title: String,
) {
    TextComponent(
        modifier = Modifier
            .fillMaxWidth()
            .height(25.dp),
        text = title,
        fontSize = T2,
        fontWeight = FontWeight.Bold,
        textColor = BLACK,
    )
}



@Preview(showBackground = true)
@Composable
private fun RecruitmentImageAreaPreview() {
    RecruitmentImageArea(
        imageUrl = "https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885__480.jpg",
        title = "title",
        tag = "active",
        type = "포트폴리오",
        onGoToPartyDetail = {}
    )
}

@Preview(showBackground = true)
@Composable
private fun ApplyButtonAreaPreview() {
    CustomButton(
        onClick = {}
    )
}