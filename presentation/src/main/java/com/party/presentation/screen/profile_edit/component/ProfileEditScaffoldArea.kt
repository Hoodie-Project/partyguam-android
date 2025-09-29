package com.party.presentation.screen.profile_edit.component

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.party.common.R
import com.party.common.component.icon.DrawableIconButton
import com.party.common.component.scaffold.ScaffoldCenterBar
import com.party.guam.design.BLACK
import com.party.guam.design.T2

@Composable
fun ProfileEditScaffoldArea(
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
                text = "프로필 편집",
                fontWeight = FontWeight.Bold,
                fontSize = T2
            )
        },
        /*actionIcons = {
            Text(
                text = "미리보기",
                fontWeight = FontWeight.Bold,
                fontSize = B2,
                color = DARK100,
                modifier = Modifier
                    .padding(end = 20.dp)
                    .clickable {
                        onGotoProfileEditPreview()
                    }
            )
        }*/
    )
}

@Preview(showBackground = true)
@Composable
private fun ProfileEditScaffoldAreaPreview() {
    ProfileEditScaffoldArea(
        onNavigationClick = {}
    )
}