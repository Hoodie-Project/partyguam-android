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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.party.common.HeightSpacer
import com.party.common.R
import com.party.common.TextComponent
import com.party.common.WidthSpacer
import com.party.common.noRippleClickable
import com.party.common.ui.theme.B2
import com.party.common.ui.theme.BLACK
import com.party.common.ui.theme.EXTRA_LARGE_BUTTON_HEIGHT2
import com.party.common.ui.theme.GRAY100
import com.party.common.ui.theme.GRAY200
import com.party.common.ui.theme.GRAY500
import com.party.common.ui.theme.LARGE_BUTTON_HEIGHT
import com.party.common.ui.theme.LARGE_CORNER_SIZE
import com.party.common.ui.theme.LIGHT100
import com.party.common.ui.theme.PRIMARY
import com.party.common.ui.theme.T3
import com.party.common.ui.theme.WHITE
import com.party.navigation.Screens


@Composable
fun TendencyBottomArea(
    navController: NavController,
    routeScreens: Screens,
    buttonText: String,
    textColor: Color,
    borderColor: Color,
    containerColor: Color,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        TendencyNextButton(
            navController = navController,
            routeScreens = routeScreens,
            text = buttonText,
            textColor = textColor,
            borderColor = borderColor,
            containerColor = containerColor
        )

        HeightSpacer(heightDp = 20.dp)

        TextComponent(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp),
            text = stringResource(id = R.string.common3),
            fontSize = B2,
            textDecoration = TextDecoration.Underline,
            textAlign = Alignment.Center,
            textColor = GRAY500,
        )

        HeightSpacer(heightDp = 20.dp)
    }
    
}
@SuppressLint("UnrememberedMutableInteractionSource")
@Composable
fun TendencyNextButton(
    modifier: Modifier = Modifier,
    navController: NavController,
    routeScreens: Screens,
    text: String,
    textColor: Color,
    containerColor: Color,
    borderColor: Color,
) {
    Button(
        onClick = {
            navController.navigate(routeScreens)
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
    text: String,
    fontWeight: FontWeight,
    iconColor: Color,
    onSelect: (String) -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(EXTRA_LARGE_BUTTON_HEIGHT2)
            .noRippleClickable {
                onSelect(text)
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
                    text = text,
                    fontWeight = fontWeight,
                    fontSize = T3
                )
            }
        }
    }
}

@Preview
@Composable
fun SelectTendencyAreaComponentPreview() {
    SelectTendencyAreaComponent(
        containerColor = GRAY100,
        text = "오전 (6시 - 12시)",
        fontWeight = FontWeight.Normal,
        iconColor = GRAY200,
        onSelect = {}
    )
}
