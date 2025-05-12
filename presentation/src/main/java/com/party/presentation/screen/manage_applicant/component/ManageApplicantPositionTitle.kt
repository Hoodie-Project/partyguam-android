package com.party.presentation.screen.manage_applicant.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.party.common.utils.TextComponent
import com.party.common.utils.WidthSpacer
import com.party.common.ui.theme.PRIMARY
import com.party.common.ui.theme.T2

@Composable
fun ManageApplicantPositionTitle(
    main: String,
    sub: String,
    recounting: Int,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        TextComponent(
            text = main,
            fontWeight = FontWeight.Bold,
            fontSize = T2,
        )
        WidthSpacer(6.dp)
        TextComponent(
            text = sub,
            fontWeight = FontWeight.Bold,
            fontSize = T2,
        )
        WidthSpacer(6.dp)
        TextComponent(
            text = recounting.toString(),
            fontWeight = FontWeight.Bold,
            fontSize = T2,
            textColor = PRIMARY,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ManageApplicantPositionTitlePreview() {
    ManageApplicantPositionTitle(
        main = "개발자",
        sub = "안드로이드",
        recounting = 0,
    )
}