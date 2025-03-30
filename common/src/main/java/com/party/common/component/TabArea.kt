package com.party.common.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.party.common.HeightSpacer
import com.party.common.TextComponent
import com.party.common.WidthSpacer
import com.party.common.noRippleClickable
import com.party.common.ui.theme.BLACK
import com.party.common.ui.theme.COMPONENT_AREA_HEIGHT
import com.party.common.ui.theme.GRAY400
import com.party.common.ui.theme.PRIMARY

val homeTopTabList = listOf("라운지", "파티", "모집공고")
val partyDetailTabList = listOf("홈", "파티원", "모집공고")

val searchTabList = listOf("전체", "파티", "모집공고")

val stateTabList = listOf("내 파티", "지원목록")

val profileEditTendencyTabList = listOf("1단계", "2단계", "3단계")

val partyRecruitmentEditTabList = listOf("전체", "모집중", "모집마감")

val notificationTabList = listOf("전체", "파티활동", "지원소식")

@Composable
fun TabArea(
    modifier: Modifier = Modifier,
    tabList: List<String>,
    selectedTabText: String,
    onTabClick: (String) -> Unit,
) {
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(COMPONENT_AREA_HEIGHT),
            horizontalArrangement = Arrangement.Start
        ) {
            tabList.forEachIndexed { index, title ->
                TabAreaItem(
                    text = title,
                    textColor = if (selectedTabText == title) BLACK else GRAY400,
                    isShowSelectedIndicate = selectedTabText == title,
                    onTabClick = { onTabClick(it) },
                )
                WidthSpacer(widthDp = 40.dp)
            }
        }
        HeightSpacer(heightDp = 4.dp)
    }
}
@Composable
fun TabAreaItem(
    text: String,
    textColor: Color,
    isShowSelectedIndicate: Boolean,
    onTabClick: (String) -> Unit,
){
    val textMeasurer = rememberTextMeasurer()
    val textWidth = textMeasurer.measure(
        text = text,
        style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
    ).size.width

    Column(
        modifier = Modifier
            .wrapContentWidth()
            .height(48.dp)
            .noRippleClickable { onTabClick(text) },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TextComponent(
            text = text,
            fontSize = 16.sp,
            textColor = textColor,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier
                .height(42.dp),
            onClick = { onTabClick(text) }
        )

        Box(
            modifier = Modifier
                .width(with(LocalDensity.current) { (textWidth + 20).toDp() })
                .height(6.dp)
                .background(if(isShowSelectedIndicate) PRIMARY else Color.Unspecified)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TabArea1Preview() {
    TabArea(
        tabList = partyDetailTabList,
        selectedTabText = "홈",
        onTabClick = {}
    )
}
@Preview(showBackground = true)
@Composable
fun TabArea2Preview() {
    TabArea(
        tabList = partyDetailTabList,
        selectedTabText = "파티원",
        onTabClick = {}
    )
}
@Preview(showBackground = true)
@Composable
fun TabArea3Preview() {
    TabArea(
        tabList = partyDetailTabList,
        selectedTabText = "모집공고",
        onTabClick = {}
    )
}

@Preview(showBackground = true)
@Composable
fun TabAreaItem1Preview() {
    TabAreaItem(
        text = "홈",
        textColor = BLACK,
        isShowSelectedIndicate = true,
        onTabClick = {},
    )
}

@Preview(showBackground = true)
@Composable
fun TabAreaItem2Preview() {
    TabAreaItem(
        text = "파티원",
        textColor = BLACK,
        isShowSelectedIndicate = true,
        onTabClick = {},
    )
}
