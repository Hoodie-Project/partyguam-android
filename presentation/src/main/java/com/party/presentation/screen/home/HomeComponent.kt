package com.party.presentation.screen.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.party.common.HeightSpacer
import com.party.common.R
import com.party.common.TextComponent
import com.party.common.ui.theme.ICON_SIZE
import com.party.common.ui.theme.T2
import com.party.common.ui.theme.T3

@Composable
fun HomeListTitleArea(
    title: String,
    description: String,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        HomeListTitleArea(title = title)

        HeightSpacer(heightDp = 8.dp)

        HomeListDescriptionArea(description = description)
    }
}

@Composable
fun HomeListTitleArea(
    title: String,
) {
    Row(
        modifier = Modifier
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
            onClick = { /*TODO*/ },
        ) {
            Icon(
                modifier = Modifier.size(ICON_SIZE),
                painter = painterResource(id = R.drawable.reload),
                contentDescription = "",
            )
        }
    }
}

@Composable
fun HomeListDescriptionArea(
    description: String,
) {
    TextComponent(
        modifier = Modifier.fillMaxWidth(),
        text = description,
        fontSize = T3,
    )
}