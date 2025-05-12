package com.party.presentation.screen.notification.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.party.common.utils.HeightSpacer
import com.party.common.utils.WidthSpacer
import com.party.common.component.notificationTabList
import com.party.common.utils.noRippleClickable
import com.party.common.ui.theme.B2
import com.party.common.ui.theme.BLACK
import com.party.common.ui.theme.GRAY200
import com.party.common.ui.theme.GRAY500
import com.party.common.ui.theme.PRIMARY
import com.party.common.ui.theme.WHITE

@Composable
fun NotificationTabArea(
    selectedTabText: String,
    onSelectTab: (String) -> Unit,
) {
    HeightSpacer(heightDp = 16.dp)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(36.dp)
            .padding(horizontal = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        notificationTabList.forEachIndexed { index, notificationText ->
            NotificationTabChip(
                modifier = Modifier,
                text = notificationText,
                containerColor = WHITE,
                contentColor = if(selectedTabText == notificationText) BLACK else GRAY500,
                borderColor = if(selectedTabText == notificationText) PRIMARY else GRAY200,
                fontWeight = if(selectedTabText == notificationText) FontWeight.SemiBold else FontWeight.Normal,
                onSelectTab = { onSelectTab(notificationText) }
            )
            WidthSpacer(widthDp = 8.dp)
        }
    }
    HeightSpacer(heightDp = 16.dp)
}

@Composable
fun NotificationTabChip(
    modifier: Modifier = Modifier,
    containerColor: Color,
    contentColor: Color,
    borderColor: Color,
    roundedCornerShape: Dp = 999.dp,
    text: String,
    fontSize: TextUnit = B2,
    fontWeight: FontWeight,
    onSelectTab: () -> Unit,
) {
    Card(
        modifier = modifier
            .wrapContentWidth()
            .height(36.dp)
            .noRippleClickable { onSelectTab() },
        colors = CardDefaults.cardColors(
            containerColor = containerColor,
            contentColor = contentColor
        ),
        shape = RoundedCornerShape(roundedCornerShape),
        border = BorderStroke(1.dp, borderColor)
    ) {
        Box(
            modifier = Modifier
                .wrapContentWidth()
                .fillMaxHeight()
                .padding(horizontal = 12.dp),
            contentAlignment = Alignment.Center
        ){
            Text(
                text = text,
                fontSize = fontSize,
                fontWeight = fontWeight,
            )
        }
    }
}