package com.party.presentation.screen.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.party.common.HeightSpacer
import com.party.common.WidthSpacer
import com.party.common.noRippleClickable
import com.party.common.ui.theme.BLACK
import com.party.common.ui.theme.COMPONENT_AREA_HEIGHT
import com.party.common.ui.theme.GRAY100
import com.party.common.ui.theme.GRAY400
import com.party.common.ui.theme.PRIMARY
import com.party.common.ui.theme.T3

val HOME_TOP_TAB_AREA_ITEM_MIN_WIDTH = 44.dp
val HOME_TOP_TAB_AREA_ITEM_MAX_WIDTH = 80.dp

@Composable
fun HomeTopTabArea(
    homeTopTabList: List<String>,
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
            homeTopTabList.forEachIndexed { index, title ->
                HomeTopTabAreaItem(
                    text = title,
                    textColor = if (selectedTabText == title) BLACK else GRAY400,
                    isShowSelectedIndicate = selectedTabText == title,
                    onTabClick = { onTabClick(it) }
                )
                WidthSpacer(widthDp = 20.dp)
            }
        }
        HeightSpacer(heightDp = 4.dp)
        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .drawBehind {
                    // 그림자를 아래 방향으로만 적용 (offsetX = 0, offsetY = 20.dp)
                    val offsetX = 0f // X축 이동 없음
                    val offsetY = 2.dp.toPx() // Y축으로 그림자 이동
                    val shadowPaint = Paint().asFrameworkPaint().apply {
                        color = android.graphics.Color.DKGRAY
                        setShadowLayer(8f, offsetX, offsetY, android.graphics.Color.DKGRAY)
                    }
                    drawIntoCanvas {
                        it.nativeCanvas.drawRoundRect(
                            0f, 0f, size.width, size.height, 30f, 30f, shadowPaint
                        )
                    }
                },
            thickness = 0.5.dp,
            color = GRAY100
        )
    }
}

@Composable
fun HomeTopTabAreaItem(
    text: String,
    textColor: Color,
    isShowSelectedIndicate: Boolean,
    onTabClick: (String) -> Unit,
) {
    Column(
        modifier = Modifier
            .widthIn(min = HOME_TOP_TAB_AREA_ITEM_MIN_WIDTH, max = HOME_TOP_TAB_AREA_ITEM_MAX_WIDTH)
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
                    .fillMaxWidth(),
                thickness = 4.dp,
                color = PRIMARY
            )
        }
    }
}