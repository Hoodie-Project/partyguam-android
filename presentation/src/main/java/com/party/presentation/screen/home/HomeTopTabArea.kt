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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.party.common.WidthSpacer
import com.party.common.noRippleClickable
import com.party.common.ui.theme.BLACK
import com.party.common.ui.theme.COMPONENT_AREA_HEIGHT
import com.party.common.ui.theme.GRAY400
import com.party.common.ui.theme.PRIMARY
import com.party.common.ui.theme.T3

val HOME_TOP_TAB_AREA_ITEM_MIN_WIDTH = 44.dp
val HOME_TOP_TAB_AREA_ITEM_MAX_WIDTH = 80.dp

@Composable
fun HomeTopTabArea(
    homeTopTabList: List<String>,
    selectedTabText: String,
    onClick: (String) -> Unit,
) {
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
                onClick = { onClick(it) }
            )
            WidthSpacer(widthDp = 20.dp)
        }
    }
}

@Composable
fun HomeTopTabAreaItem(
    text: String,
    textColor: Color,
    isShowSelectedIndicate: Boolean,
    onClick: (String) -> Unit,
) {
    Column(
        modifier = Modifier
            .widthIn(min = HOME_TOP_TAB_AREA_ITEM_MIN_WIDTH, max = HOME_TOP_TAB_AREA_ITEM_MAX_WIDTH)
            .height(COMPONENT_AREA_HEIGHT)
            .noRippleClickable { onClick(text) }
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
                    .fillMaxWidth()
                    .height(4.dp),
                color = PRIMARY
            )
        }
    }
}