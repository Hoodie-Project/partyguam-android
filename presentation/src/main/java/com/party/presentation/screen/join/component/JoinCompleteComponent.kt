package com.party.presentation.screen.join.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.party.common.R
import com.party.common.utils.WidthSpacer
import com.party.common.utils.fs
import com.party.guam.design.B2
import com.party.guam.design.BLACK
import com.party.guam.design.PRIMARY
import com.party.guam.design.WHITE

@Composable
fun RowButtonArea(
    onGotoHome: () -> Unit,
    onGotoDetailProfile: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        JoinScreenButton(
            modifier = Modifier.weight(0.5f),
            buttonText = stringResource(id = R.string.join_complete2),
            buttonTextColor = BLACK,
            buttonContainerColor = WHITE,
            buttonBorderColor = PRIMARY,
            fontSize = fs(B2),
            fontWeight = FontWeight.Bold,
            onClick = onGotoHome
        )

        WidthSpacer(widthDp = 8.dp)

        JoinScreenButton(
            modifier = Modifier.weight(0.5f),
            buttonText = stringResource(id = R.string.join_complete3),
            buttonTextColor = BLACK ,
            buttonContainerColor = PRIMARY,
            buttonBorderColor = PRIMARY,
            fontSize = fs(B2),
            fontWeight = FontWeight.Bold,
            onClick = onGotoDetailProfile
        )
    }
}

@Preview
@Composable
fun RowButtonAreaPreview() {
    RowButtonArea(
        onGotoHome = {},
        onGotoDetailProfile = {}
    )
}