package com.party.presentation.screen.party_create.component

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.party.common.R
import com.party.common.component.icon.DrawableIconButton
import com.party.common.component.scaffold.ScaffoldCenterBar
import com.party.common.ui.theme.BLACK
import com.party.common.ui.theme.T2

@Composable
fun PartyCreateScaffoldArea(
    isShowDialog: Boolean,
    isShowCompleteDialog: Boolean,
    onNavigationClick: () -> Unit,
) {
    ScaffoldCenterBar(
        isShowDialog = isShowDialog,
        isShowCompleteDialog = isShowCompleteDialog,
        navigationIcon = {
            DrawableIconButton(
                icon = painterResource(id = R.drawable.arrow_back),
                iconColor = BLACK,
                iconSize = 24.dp,
                contentDescription = "back",
                onClick = { onNavigationClick() }
            )
        },
        title = {
            Text(
                text = "파티 생성",
                fontWeight = FontWeight.Bold,
                fontSize = T2
            )
        },
    )
}