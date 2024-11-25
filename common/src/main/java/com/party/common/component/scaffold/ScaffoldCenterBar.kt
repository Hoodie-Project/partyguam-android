package com.party.common.component.scaffold

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.party.common.R
import com.party.common.component.icon.DrawableIconButton
import com.party.common.ui.theme.BLACK
import com.party.common.ui.theme.T2
import com.party.common.ui.theme.WHITE

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScaffoldCenterBar(
    isShowDialog: Boolean = false,
    isShowCompleteDialog: Boolean = false,
    title: @Composable () -> Unit = {},
    navigationIcon: @Composable () -> Unit = {},
    actionIcons: @Composable () -> Unit = {},
) {
    CenterAlignedTopAppBar(
        modifier = Modifier
            .fillMaxWidth()
            .blur(
                radiusX = if (isShowDialog || isShowCompleteDialog) 10.dp else 0.dp,
                radiusY = if (isShowDialog || isShowCompleteDialog) 10.dp else 0.dp,
            ),
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = WHITE
        ),
        title = title,
        navigationIcon = navigationIcon,
        actions = { actionIcons() },
    )

    if(isShowDialog || isShowCompleteDialog){
        // 상태 표시줄 높이 가져오기
        val statusBarHeight: Dp = with(LocalDensity.current) {
            WindowInsets.statusBars.asPaddingValues().calculateTopPadding()
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp + statusBarHeight)
                .background(BLACK.copy(alpha = 0.7f))
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ScaffoldCenterBarPreview(){
    ScaffoldCenterBar(
        isShowDialog = true,
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