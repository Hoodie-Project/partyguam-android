package com.party.presentation.screen.profile_edit_tendency.component

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.party.common.R
import com.party.common.component.icon.DrawableIconButton
import com.party.common.component.scaffold.ScaffoldCenterBar
import com.party.common.utils.fs
import com.party.guam.design.B2
import com.party.guam.design.BLACK
import com.party.guam.design.T2

@Composable
fun ProfileEditTendencyScaffoldArea(
    onNavigationClick: () -> Unit,
) {
    ScaffoldCenterBar(
        navigationIcon = {
            DrawableIconButton(
                icon = painterResource(id = R.drawable.icon_arrow_back),
                iconColor = BLACK,
                iconSize = 24.dp,
                contentDescription = "back",
                onClick = { onNavigationClick() }
            )
        },
        title = {
            Text(
                text = "성향",
                fontWeight = FontWeight.Bold,
                fontSize = fs(T2)
            )
        },
    )
}

@Preview(showBackground = true)
@Composable
private fun ProfileEditTendencyScaffoldAreaPreview() {
    ProfileEditTendencyScaffoldArea(
        onNavigationClick = {}
    )
}