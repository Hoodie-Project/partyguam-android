package com.party.presentation.screen.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.party.common.HeightSpacer
import com.party.common.R
import com.party.common.TextComponent
import com.party.common.WidthSpacer
import com.party.common.component.Category
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
    onClick: () -> Unit,
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
            onClick = onClick,
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
            onClick = { onClick() },
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
) {
    TextComponent(
        modifier = modifier
            .fillMaxWidth(),
        text = "$main | $sub",
        fontSize = B2,
        textColor = textColor,
    )
}

@Composable
fun RecruitmentCountArea(
    modifier: Modifier,
    recruitingCount: Int,
    recruitedCount: Int,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.End,
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = horizontalArrangement,
    ) {
        TextComponent(
            text = stringResource(id = R.string.home_common),
            fontSize = B3,
        )

        WidthSpacer(widthDp = 4.dp)

        TextComponent(
            text = "$recruitedCount / $recruitingCount",
            fontSize = B3,
            textColor = RED,
        )
    }
}

@Composable
fun PartyCategory(
    category: String,
) {
    Category(
        containerColor = TYPE_COLOR_BACKGROUND,
        contentColor = TYPE_COLOR_TEXT,
        text = category,
    )
}