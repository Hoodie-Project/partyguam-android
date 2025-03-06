package com.party.presentation.screen.detail.select_tendency

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircleOutline
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.party.common.HeightSpacer
import com.party.common.R
import com.party.common.TextComponent
import com.party.common.WidthSpacer
import com.party.common.noRippleClickable
import com.party.common.ui.theme.B2
import com.party.common.ui.theme.COMPONENT_AREA_HEIGHT
import com.party.common.ui.theme.GRAY200
import com.party.common.ui.theme.GRAY500
import com.party.common.ui.theme.LARGE_BUTTON_HEIGHT
import com.party.common.ui.theme.LARGE_CORNER_SIZE
import com.party.common.ui.theme.T3
import com.party.domain.model.user.detail.PersonalityListOption
import com.party.domain.model.user.detail.PersonalitySaveRequest2


@Composable
fun TendencyBottomArea(
    buttonText: String,
    textColor: Color,
    borderColor: Color,
    containerColor: Color,
    onClick: () -> Unit,
    onSkip: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        TendencyNextButton(
            text = buttonText,
            textColor = textColor,
            borderColor = borderColor,
            containerColor = containerColor,
            onClick = onClick
        )

        HeightSpacer(heightDp = 20.dp)

        TextComponent(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp),
            text = stringResource(id = R.string.common3),
            fontSize = B2,
            textDecoration = TextDecoration.Underline,
            align = Alignment.Center,
            textColor = GRAY500,
            onClick = onSkip
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

@Composable
fun SelectTendencyAreaComponent(
    containerColor: Color,
    item: PersonalityListOption,
    fontWeight: FontWeight,
    iconColor: Color,
    textColor: Color,
    onSelect: (PersonalityListOption) -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(COMPONENT_AREA_HEIGHT)
            .noRippleClickable {
                onSelect(item)
            },
        shape = RoundedCornerShape(LARGE_CORNER_SIZE),
        border = BorderStroke(1.dp, GRAY200),
        elevation = CardDefaults.cardElevation(2.dp),
        colors = CardDefaults.cardColors(
            containerColor = containerColor
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ){
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 20.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    imageVector = Icons.Default.CheckCircleOutline,
                    contentDescription = "check",
                    tint = iconColor,
                    modifier = Modifier
                        .size(20.dp)
                )
                WidthSpacer(widthDp = 6.dp)
                Text(
                    text = item.content,
                    fontWeight = fontWeight,
                    fontSize = T3,
                    color = textColor
                )
            }
        }
    }
}

object SavePersonalityData {
    var personalitySaveRequest1 by mutableStateOf(PersonalitySaveRequest2(personalityQuestionId = 0, personalityOptionId = emptyList()))
    var personalitySaveRequest2 by mutableStateOf(PersonalitySaveRequest2(personalityQuestionId = 0, personalityOptionId = emptyList()))
    var personalitySaveRequest3 by mutableStateOf(PersonalitySaveRequest2(personalityQuestionId = 0, personalityOptionId = emptyList()))
    var personalitySaveRequest4 by mutableStateOf(PersonalitySaveRequest2(personalityQuestionId = 0, personalityOptionId = emptyList()))

}