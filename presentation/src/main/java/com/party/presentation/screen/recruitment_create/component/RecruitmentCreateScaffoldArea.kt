package com.party.presentation.screen.recruitment_create.component

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.party.common.R
import com.party.common.component.icon.DrawableIconButton
import com.party.common.component.scaffold.ScaffoldCenterBar
import com.party.common.utils.noRippleClickable
import com.party.common.ui.theme.B2
import com.party.common.ui.theme.BLACK
import com.party.common.ui.theme.PRIMARY
import com.party.common.ui.theme.T2

@Composable
fun RecruitmentCreateScaffoldArea(
    onNavigationClick: () -> Unit,
    onRecruitmentPreview: () -> Unit,
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
                text = "모집 추가",
                fontWeight = FontWeight.Bold,
                fontSize = T2
            )
        },
        actionIcons = {
            Text(
                text = "미리보기",
                fontWeight = FontWeight.Bold,
                fontSize = B2,
                color = PRIMARY,
                modifier = Modifier
                    .padding(end = 20.dp)
                    .noRippleClickable {
                        onRecruitmentPreview()
                    },
            )
        }
    )
}