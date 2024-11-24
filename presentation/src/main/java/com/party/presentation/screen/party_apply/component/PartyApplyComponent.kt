package com.party.presentation.screen.party_apply.component

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.party.common.HeightSpacer
import com.party.common.TextComponent
import com.party.common.WidthSpacer
import com.party.common.component.input_field.MultiLineInputField
import com.party.common.ui.theme.B2
import com.party.common.ui.theme.B3
import com.party.common.ui.theme.BLACK
import com.party.common.ui.theme.EXTRA_LARGE_BUTTON_HEIGHT2
import com.party.common.ui.theme.GRAY200
import com.party.common.ui.theme.GRAY400
import com.party.common.ui.theme.GREEN
import com.party.common.ui.theme.LARGE_CORNER_SIZE
import com.party.common.ui.theme.PRIMARY
import com.party.common.ui.theme.RED
import com.party.common.ui.theme.T2
import com.party.common.ui.theme.T3
import com.party.common.ui.theme.WHITE
import com.party.common.ui.theme.YELLOW

@Composable
fun PartyApplyTitleArea() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        TextComponent(
            text = "지원 사유",
            fontSize = T2,
            fontWeight = FontWeight.Bold,
            textColor = BLACK,
        )
        HeightSpacer(heightDp = 4.dp)
        TextComponent(
            text = "250자 이내로 자유롭게 작성해 주세요",
            fontSize = B2,
            textColor = BLACK,
        )
    }
}

@Composable
fun PartyApplyInputReasonArea(
    inputText: String,
    onValueChange: (String) -> Unit,
    onAllDeleteInputText: () -> Unit,
) {
    val placeHolder = "지원 사유를 작성해 주세요."

    MultiLineInputField(
        placeHolder = placeHolder,
        inputText = inputText,
        onValueChange = onValueChange,
        onAllDeleteInputText = onAllDeleteInputText
    )
}

@Preview
@Composable
fun PartyApplyTitleAreaPreview() {
    PartyApplyTitleArea()
}

@Preview
@Composable
fun PartyApplyInputReasonAreaPreview(
    modifier: Modifier = Modifier
) {
    PartyApplyInputReasonArea(
        inputText = "",
        onValueChange = {},
        onAllDeleteInputText = {}
    )
}