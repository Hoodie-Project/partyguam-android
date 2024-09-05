package com.party.presentation.screen.join

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.party.common.WidthSpacer
import com.party.common.ui.theme.EXTRA_LARGE_BUTTON_HEIGHT2
import com.party.common.ui.theme.GRAY100
import com.party.common.ui.theme.GRAY200
import com.party.common.ui.theme.GRAY400
import com.party.common.ui.theme.GRAY500
import com.party.common.ui.theme.LARGE_BUTTON_HEIGHT
import com.party.common.ui.theme.LARGE_CORNER_SIZE
import com.party.common.ui.theme.MEDIUM_PADDING_SIZE
import com.party.common.ui.theme.T3
import com.party.navigation.Screens

@SuppressLint("UnrememberedMutableInteractionSource")
@Composable
fun JoinScreenButton(
    modifier: Modifier,
    buttonText: String,
    buttonTextColor: Color,
    buttonContainerColor: Color,
    buttonBorderColor: Color,
    fontSize: TextUnit,
    fontWeight: FontWeight,
    navController: NavHostController,
    routeScreen: Screens,
) {
    Button(
        onClick = {
            navController.navigate(routeScreen)
        },
        modifier = modifier
            .height(LARGE_BUTTON_HEIGHT),
        shape = RoundedCornerShape(LARGE_CORNER_SIZE),
        colors = ButtonDefaults.buttonColors(
            containerColor = buttonContainerColor,
            contentColor = buttonTextColor
        ),
        border = BorderStroke(1.dp, buttonBorderColor),
        interactionSource = MutableInteractionSource(), // Button Ripple 효과 없애기
    ) {
        Text(
            text = buttonText,
            fontSize = fontSize,
            fontWeight = fontWeight
        )
    }
}

@Composable
fun JoinScreenInputField(
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    inputText: String,
    textColor: Color,
    containerColor: Color,
    borderColor: Color,
    closeIcon: Painter?,
    readOnly: Boolean,
    placeHolder: String,
    onString: (String) -> Unit,
) {
    BasicTextField(
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = imeAction
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(EXTRA_LARGE_BUTTON_HEIGHT2)
            .clip(RoundedCornerShape(LARGE_CORNER_SIZE))
            .border(BorderStroke(1.dp, borderColor), RoundedCornerShape(LARGE_CORNER_SIZE))
            .background(containerColor),
        value = inputText,
        onValueChange = { onString(it) },
        readOnly = readOnly,
        maxLines = 1,
        singleLine = true,
        textStyle = TextStyle(
            color = textColor,
            fontSize = T3,
        ),
        cursorBrush = SolidColor(Color.Black),
        decorationBox = { innerTextField ->
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                WidthSpacer(widthDp = MEDIUM_PADDING_SIZE)

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .wrapContentHeight(),
                ){
                    innerTextField()
                    if(inputText.isEmpty()){
                        Text(
                            text = placeHolder,
                            fontSize = T3,
                            color = textColor,
                        )
                    }
                }

                closeIcon?.let {
                    IconButton(
                        onClick = { onString("") }
                    ) {
                        Icon(
                            painter = closeIcon,
                            contentDescription = "close",
                            modifier = Modifier.size(20.dp),
                            tint = GRAY400
                        )
                    }
                }
            }
        }
    )
}

@Preview
@Composable
fun JoinScreenInputFieldPreview() {
    JoinScreenInputField(
        closeIcon = null,
        readOnly = true,
        inputText = "",
        textColor = GRAY500,
        containerColor = GRAY100,
        borderColor = GRAY200,
        placeHolder = "",
        onString = {  }
    )
}