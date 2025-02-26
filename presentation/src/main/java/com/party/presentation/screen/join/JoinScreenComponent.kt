package com.party.presentation.screen.join

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
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
    onClick: () -> Unit,
) {
    Button(
        onClick = { onClick() },
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
    visualTransformation: VisualTransformation = VisualTransformation.None,
    onString: (String) -> Unit,
) {
    val focusManager = LocalFocusManager.current  // 키보드 내리기 위한 FocusManager

    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                focusManager.clearFocus()  // 필드 외부 터치 시 포커스 해제 → 키보드 내려감
            }
    ) {
        BasicTextField(
            visualTransformation = visualTransformation,
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
                    ) {
                        innerTextField()
                        if (inputText.isEmpty()) {
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

class TimeTransFormation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        return dateFilter(text)
    }
}

fun dateFilter(text: AnnotatedString): TransformedText {
    val trimmed = if (text.text.length >= 8) text.text.substring(0..7) else text.text
    var out = ""
    for (i in trimmed.indices) {
        out += trimmed[i]
        if (i == 3 || i == 5) out += "-"
    }

    val numberOffsetTranslator = object : OffsetMapping {
        override fun originalToTransformed(offset: Int): Int {
            if (offset <= 4) return offset
            if (offset <= 5) return offset + 1
            if (offset <= 8) return offset + 2
            return 10
        }

        override fun transformedToOriginal(offset: Int): Int {
            if (offset <= 2) return offset
            if (offset <= 5) return offset - 1
            if (offset <= 10) return offset - 2
            return 8
        }
    }

    return TransformedText(AnnotatedString(out), numberOffsetTranslator)
}