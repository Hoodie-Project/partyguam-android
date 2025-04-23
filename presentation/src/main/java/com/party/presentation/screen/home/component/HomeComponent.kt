package com.party.presentation.screen.home.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.party.common.HeightSpacer
import com.party.common.R
import com.party.common.TextComponent
import com.party.common.WidthSpacer
import com.party.common.component.chip.Chip
import com.party.common.noRippleClickable
import com.party.common.ui.theme.B2
import com.party.common.ui.theme.B3
import com.party.common.ui.theme.BLACK
import com.party.common.ui.theme.ICON_SIZE
import com.party.common.ui.theme.RED
import com.party.common.ui.theme.T2
import com.party.common.ui.theme.T3
import com.party.common.ui.theme.TYPE_COLOR_BACKGROUND
import com.party.common.ui.theme.TYPE_COLOR_TEXT

@Composable
fun HomeListTitleArea(
    title: String,
    titleIcon: Painter,
    description: String,
    onReload: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(55.dp),
    ) {
        HomeListTitleArea(
            modifier = Modifier.height(25.dp),
            title = title,
            icon = titleIcon,
            onClick = onReload,
        )
        HeightSpacer(heightDp = 8.dp)
        HomeListDescriptionArea(
            modifier = Modifier.height(22.dp),
            description = description
        )
    }
}

@Composable
fun HomeListTitleArea(
    modifier: Modifier,
    title: String,
    icon: Painter,
    onClick: () -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        TextComponent(
            text = title,
            fontWeight = FontWeight.Bold,
            fontSize = T2,
        )

        IconButton(
            onClick = onClick,
        ) {
            Icon(
                modifier = Modifier.size(ICON_SIZE),
                painter = icon,
                contentDescription = "",
            )
        }
    }
}

@Composable
fun HomeListDescriptionArea(
    modifier: Modifier,
    description: String,
) {
    TextComponent(
        modifier = modifier.fillMaxWidth(),
        text = description,
        fontSize = T3,
    )
}

@Composable
fun PositionArea(
    modifier: Modifier,
    main: String,
    sub: String,
    textColor: Color = BLACK,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(20.dp)
            .noRippleClickable {
                onClick()
            },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        TextComponent(
            modifier = modifier.height(20.dp),
            text = main,
            fontSize = B2,
            textColor = textColor,
            onClick = onClick
        )
        WidthSpacer(widthDp = 6.dp)
        Image(
            painter = painterResource(id = R.drawable.vertical_rectangle),
            contentDescription = "",
            modifier = Modifier
                .width(1.dp)
                .height(11.dp)
                .padding(top = 2.dp),
        )
        WidthSpacer(widthDp = 2.dp)
        TextComponent(
            modifier = modifier.height(20.dp),
            text = sub,
            fontSize = B2,
            textColor = textColor,
            onClick = onClick
        )
    }
}

@Composable
fun RecruitmentCountArea(
    modifier: Modifier,
    recruitingCount: Int,
    recruitedCount: Int,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.End,
    onClick: () -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .noRippleClickable{
                onClick()
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = horizontalArrangement,
    ) {
        TextComponent(
            text = stringResource(id = R.string.home_common),
            fontSize = B3,
            onClick = onClick
        )

        WidthSpacer(widthDp = 4.dp)

        TextComponent(
            text = "$recruitedCount / $recruitingCount",
            fontSize = B3,
            textColor = RED,
            onClick = onClick
        )
    }
}

@Composable
fun PartyCategory(
    category: String,
) {
    Chip(
        containerColor = TYPE_COLOR_BACKGROUND,
        contentColor = TYPE_COLOR_TEXT,
        text = category,
    )
}