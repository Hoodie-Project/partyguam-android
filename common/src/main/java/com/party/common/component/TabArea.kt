package com.party.common.component

import androidx.compose.foundation.Canvas
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.party.common.HeightSpacer
import com.party.common.WidthSpacer
import com.party.common.noRippleClickable
import com.party.common.ui.theme.BLACK
import com.party.common.ui.theme.COMPONENT_AREA_HEIGHT
import com.party.common.ui.theme.GRAY400
import com.party.common.ui.theme.PRIMARY
import com.party.common.ui.theme.T3

val TAB_AREA_ITEM_MIN_WIDTH = 40.dp
val TAB_AREA_ITEM_MAX_WIDTH = 68.dp

val homeTopTabList = listOf("홈", "파티", "모집공고")
val partyDetailTabList = listOf("홈", "파티원", "모집공고")

@Composable
fun TabArea(
    tabList: List<String>,
    selectedTabText: String,
    onTabClick: (String) -> Unit,
) {
    Column {
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
                    onTabClick = { onTabClick(it) }
                )
                WidthSpacer(widthDp = 20.dp)
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
    Column(
        modifier = Modifier
            .widthIn(min = TAB_AREA_ITEM_MIN_WIDTH, max = TAB_AREA_ITEM_MAX_WIDTH)
            .height(COMPONENT_AREA_HEIGHT)
            .noRippleClickable { onTabClick(text) }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center,
        ){
            Text(
                text = text,
                color = textColor,
                fontWeight = FontWeight.SemiBold,
                fontSize = T3
            )
        }
        if (isShowSelectedIndicate) {
            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .align(Alignment.CenterHorizontally),
                thickness = 4.dp,
                color = PRIMARY
            )
        }
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
        onTabClick = {}
    )
}

@Preview(showBackground = true)
@Composable
fun TabAreaItem2Preview() {
    TabAreaItem(
        text = "파티원",
        textColor = BLACK,
        isShowSelectedIndicate = true,
        onTabClick = {}
    )
}
