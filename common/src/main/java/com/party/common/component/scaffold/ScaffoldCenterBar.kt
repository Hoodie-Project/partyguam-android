package com.party.common.component.scaffold

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.party.common.R
import com.party.common.component.icon.DrawableIconButton
import com.party.common.ui.theme.BLACK
import com.party.common.ui.theme.T2
import com.party.common.ui.theme.WHITE

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScaffoldCenterBar(
    modifier: Modifier = Modifier,
    title: @Composable () -> Unit = {},
    navigationIcon: @Composable () -> Unit = {},
    actionIcons: @Composable () -> Unit = {},
) {
    CenterAlignedTopAppBar(
        modifier = modifier
            .fillMaxWidth(),
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = WHITE
        ),
        title = title,
        navigationIcon = navigationIcon,
        actions = { actionIcons() },
    )
}

@Preview(showBackground = true)
@Composable
fun ScaffoldCenterBarPreview(){
    ScaffoldCenterBar(
        title = {
            Text(
                text = "파티 생성",
                fontWeight = FontWeight.Bold,
                fontSize = T2
            )
        },
        navigationIcon = {
            DrawableIconButton(
                icon = painterResource(id = R.drawable.arrow_back),
                iconColor = BLACK,
                iconSize = 24.dp,
                contentDescription = "back",
                onClick = {  }
            )
        },
        actionIcons = {
            // Action Icons
        }
    )
}