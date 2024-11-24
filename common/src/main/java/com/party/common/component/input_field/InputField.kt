package com.party.common.component.input_field

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.party.common.R
import com.party.common.WidthSpacer
import com.party.common.component.icon.DrawableIconButton
import com.party.common.ui.theme.BLACK
import com.party.common.ui.theme.GRAY200
import com.party.common.ui.theme.INPUT_FIELD_HEIGHT
import com.party.common.ui.theme.LARGE_CORNER_SIZE
import com.party.common.ui.theme.MEDIUM_PADDING_SIZE
import com.party.common.ui.theme.PRIMARY
import com.party.common.ui.theme.T3
import com.party.common.ui.theme.WHITE

@Composable
fun InputField(
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    elevation: Dp = 4.dp,
    inputText: String,
    inputTextColor: Color = BLACK,
    containerColor: Color = WHITE,
    borderColor: Color = GRAY200,
    readOnly: Boolean = false,
    placeHolder: String,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    onValueChange: (String) -> Unit,
    icon: @Composable () -> Unit = {},
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = elevation,
                shape = RoundedCornerShape(LARGE_CORNER_SIZE),
            ),
    ) {
        BasicTextField(
            visualTransformation = visualTransformation,
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType,
                imeAction = imeAction
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(INPUT_FIELD_HEIGHT)
                .clip(RoundedCornerShape(LARGE_CORNER_SIZE))
                .border(BorderStroke(1.dp, borderColor), RoundedCornerShape(LARGE_CORNER_SIZE))
                .background(containerColor)
            ,
            value = inputText,
            onValueChange = { onValueChange(it) },
            readOnly = readOnly,
            maxLines = 1,
            singleLine = true,
            textStyle = TextStyle(
                color = inputTextColor,
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
                                color = inputTextColor,
                            )
                        }
                    }
                    icon()
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun InputFieldPreview() {
    InputField(
        inputText = "",
        inputTextColor = Color.Black,
        containerColor = Color.White,
        borderColor = Color.Black,
        readOnly = false,
        placeHolder = "값을 입력해주세요",
        onValueChange = {},
        icon = {
            DrawableIconButton(
                icon = painterResource(id = R.drawable.emergency),
                contentDescription = "",
                onClick = {}
            )
        }
    )
}

@Preview(showBackground = true)
@Composable
fun InputFieldPreview2() {
    InputField(
        inputText = "입력값을 입력했어요",
        inputTextColor = Color.Black,
        containerColor = Color.White,
        borderColor = PRIMARY,
        readOnly = false,
        placeHolder = "값을 입력해주세요",
        onValueChange = {},
        icon = {
            DrawableIconButton(
                icon = painterResource(id = R.drawable.close),
                contentDescription = "",
                onClick = {}
            )
        }
    )
}
