package com.party.presentation.screen.detail.component

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.party.common.R
import com.party.common.utils.WidthSpacer
import com.party.guam.design.B2
import com.party.guam.design.BLACK
import com.party.guam.design.LARGE_BUTTON_HEIGHT
import com.party.guam.design.LARGE_CORNER_SIZE
import com.party.guam.design.PRIMARY
import com.party.guam.design.WHITE

@Composable
fun FinishButtonArea(
    onGoProfile: () -> Unit,
    onGoHome: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        TendencyNextButton(
            modifier = Modifier.weight(0.5f),
            text = stringResource(id = R.string.select_tendency9),
            textColor = BLACK,
            containerColor = WHITE,
            borderColor = PRIMARY,
            onClick = onGoProfile
        )

        WidthSpacer(widthDp = 8.dp)

        TendencyNextButton(
            modifier = Modifier.weight(0.5f),
            text = stringResource(id = R.string.common4),
            textColor = BLACK,
            containerColor = PRIMARY,
            borderColor = PRIMARY,
            onClick = onGoHome
        )
    }
}


@SuppressLint("UnrememberedMutableInteractionSource")
@Composable
fun TendencyNextButton(
    modifier: Modifier = Modifier,
    text: String,
    textColor: Color,
    containerColor: Color,
    borderColor: Color,
    onClick: () -> Unit = {},
) {
    Button(
        onClick = {
            onClick()
        },
        modifier = modifier
            .fillMaxWidth()
            .height(LARGE_BUTTON_HEIGHT),
        shape = RoundedCornerShape(LARGE_CORNER_SIZE),
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor
        ),
        border = BorderStroke(1.dp, borderColor),
        interactionSource = MutableInteractionSource(), // Button Ripple 효과 없애기
    ){
        Text(
            text = text,
            color = textColor,
            fontSize = B2,
            fontWeight = FontWeight.Bold,
        )
    }
}