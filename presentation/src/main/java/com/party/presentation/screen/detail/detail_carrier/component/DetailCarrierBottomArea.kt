package com.party.presentation.screen.detail.detail_carrier.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.party.common.DetailCarrierData.mainSelectedCarrier
import com.party.common.DetailCarrierData.mainSelectedDetailPosition
import com.party.common.DetailCarrierData.mainSelectedMainPosition
import com.party.common.HeightSpacer
import com.party.common.R
import com.party.common.TextComponent
import com.party.common.ui.theme.B2
import com.party.common.ui.theme.BLACK
import com.party.common.ui.theme.GRAY400
import com.party.common.ui.theme.GRAY500
import com.party.common.ui.theme.LIGHT400
import com.party.common.ui.theme.PRIMARY
import com.party.presentation.screen.detail.DetailProfileNextButton

@Composable
fun DetailCarrierBottomArea(
    onSkip: () -> Unit,
    onNextButtonClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        DetailProfileNextButton(
            text = stringResource(id = R.string.common1),
            textColor = if(isValidNextButton()) BLACK else GRAY400,
            containerColor = if(isValidNextButton()) PRIMARY else LIGHT400,
            onClick = onNextButtonClick
        )

        HeightSpacer(heightDp = 12.dp)

        TextComponent(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp),
            text = stringResource(id = R.string.common3),
            fontSize = B2,
            textColor = GRAY500,
            textAlign = Alignment.Center,
            textDecoration = TextDecoration.Underline,
            onClick = onSkip
        )
    }
}

fun isValidNextButton(): Boolean{
    return mainSelectedCarrier.isNotEmpty() && mainSelectedMainPosition.isNotEmpty() && mainSelectedDetailPosition.isNotEmpty()
}