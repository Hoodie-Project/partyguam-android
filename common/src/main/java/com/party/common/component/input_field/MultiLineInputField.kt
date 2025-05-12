package com.party.common.component.input_field

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.party.common.utils.HeightSpacer
import com.party.common.utils.TextComponent
import com.party.common.utils.WidthSpacer
import com.party.common.ui.theme.B3
import com.party.common.ui.theme.BLACK
import com.party.common.ui.theme.EXTRA_LARGE_BUTTON_HEIGHT2
import com.party.common.ui.theme.GRAY200
import com.party.common.ui.theme.GRAY400
import com.party.common.ui.theme.LARGE_CORNER_SIZE
import com.party.common.ui.theme.PRIMARY
import com.party.common.ui.theme.RED
import com.party.common.ui.theme.T3
import com.party.common.ui.theme.WHITE

@Composable
fun MultiLineInputField(
    placeHolder: String,
    inputText: String,
    onValueChange: (String) -> Unit,
    onAllDeleteInputText: () -> Unit,
) {

    val borderColor = if(inputText.isEmpty()) GRAY200 else PRIMARY
    val elevation = if(inputText.isEmpty()) 4.dp else 0.dp

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .shadow(elevation, shape = RoundedCornerShape(12.dp), clip = true)
        ) {
            BasicTextField(
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.None
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .height(EXTRA_LARGE_BUTTON_HEIGHT2)
                    .clip(RoundedCornerShape(LARGE_CORNER_SIZE))
                    .border(BorderStroke(1.dp, borderColor), RoundedCornerShape(LARGE_CORNER_SIZE))
                    .background(WHITE),
                value = inputText,
                onValueChange = onValueChange,
                maxLines = 100,
                singleLine = false,
                textStyle = TextStyle(
                    color = BLACK,
                    fontSize = T3,
                ),
                cursorBrush = SolidColor(Color.Black),
                decorationBox = { innerTextField ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(20.dp)
                    ){
                        innerTextField()
                        if(inputText.isEmpty()){
                            Text(
                                text = placeHolder,
                                fontSize = T3,
                                color = GRAY400,
                            )
                        }
                    }
                }
            )
        }

        HeightSpacer(heightDp = 12.dp)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End,
        ) {
            TextComponent(
                text = "${inputText.length} / 250",
                fontSize = B3,
                textColor = GRAY400,
            )
            WidthSpacer(widthDp = 8.dp)
            TextComponent(
                text = "전체삭제",
                fontSize = B3,
                textColor = RED,
                onClick = onAllDeleteInputText,
                modifier = Modifier.offset(y = (-1).dp)
            )
        }
        HeightSpacer(heightDp = 18.dp)
    }
}

@Preview(showBackground = true)
@Composable
fun MultiLineInputFieldPreview() {
    MultiLineInputField(
        placeHolder = "값을 입력해주세요값을 입력해주세요값을 입력해주세요값을 입력해주세요값을 입력해주세요",
        inputText = "",
        onValueChange = {},
        onAllDeleteInputText = {}
    )
}