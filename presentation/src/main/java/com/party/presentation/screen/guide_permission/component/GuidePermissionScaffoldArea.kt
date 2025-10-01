package com.party.presentation.screen.guide_permission.component

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import com.party.common.component.scaffold.ScaffoldCenterBar
import com.party.common.utils.fs
import com.party.guam.design.B2
import com.party.guam.design.T2

@Composable
fun GuidePermissionScaffoldArea() {
    ScaffoldCenterBar(
        title = {
            Text(
                text = "앱 접근 권한 안내",
                fontWeight = FontWeight.Bold,
                fontSize = fs(T2)
            )
        },
    )
}