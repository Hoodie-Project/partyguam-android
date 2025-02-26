package com.party.presentation.screen.detail.detail_carrier.component

import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.party.common.DetailCarrierData.mainSelectedDetailPosition
import com.party.common.DetailCarrierData.mainSelectedCarrier
import com.party.common.DetailCarrierData.mainSelectedDetailPositionId
import com.party.common.DetailCarrierData.mainSelectedMainPosition
import com.party.common.DetailCarrierData.subSelectedCarrier
import com.party.common.DetailCarrierData.subSelectedDetailPosition
import com.party.common.DetailCarrierData.subSelectedDetailPositionId
import com.party.common.DetailCarrierData.subSelectedMainPosition
import com.party.common.HeightSpacer
import com.party.common.R
import com.party.common.TextComponent
import com.party.common.WidthSpacer
import com.party.common.noRippleClickable
import com.party.common.ui.theme.B2
import com.party.common.ui.theme.GRAY200
import com.party.common.ui.theme.GRAY500
import com.party.common.ui.theme.LARGE_BUTTON_HEIGHT
import com.party.common.ui.theme.LARGE_CORNER_SIZE
import com.party.common.ui.theme.T3
import com.party.common.ui.theme.WHITE

@Composable
fun PositionArea(
    context: Context,
    onGoToChoiceCarrierPosition: (Boolean) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        PositionAreaComponent(
            context = context,
            title = stringResource(id = R.string.detail_carrier3),
            selectedCarrier = mainSelectedCarrier,
            selectedPosition = mainSelectedMainPosition,
            selectedDetailPosition = mainSelectedDetailPosition,
            onGoToChoiceCarrierPosition = { onGoToChoiceCarrierPosition(true) },
            onReset = {
                mainSelectedCarrier = ""
                mainSelectedMainPosition = ""
                mainSelectedDetailPosition = ""
                mainSelectedDetailPositionId = 0
            }
        )
        HeightSpacer(heightDp = 40.dp)
        PositionAreaComponent(
            context = context,
            title = stringResource(id = R.string.detail_carrier4),
            selectedCarrier = subSelectedCarrier,
            selectedPosition = subSelectedMainPosition,
            selectedDetailPosition = subSelectedDetailPosition,
            onGoToChoiceCarrierPosition = { onGoToChoiceCarrierPosition(false) },
            onReset = {
                subSelectedCarrier = ""
                subSelectedMainPosition = ""
                subSelectedDetailPosition = ""
                subSelectedDetailPositionId = 0
            }
        )
    }
}

@Composable
fun PositionAreaComponent(
    context: Context,
    title: String,
    selectedCarrier: String,
    selectedPosition: String,
    selectedDetailPosition: String,
    onGoToChoiceCarrierPosition: () -> Unit,
    onReset: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        TextComponent(
            text = title,
            fontSize = T3,
        )
        HeightSpacer(heightDp = 12.dp)
        AddCarrierCard(
            context = context,
            selectedCarrier = selectedCarrier,
            selectedPosition = selectedPosition,
            selectedDetailPosition = selectedDetailPosition,
            onGoToChoiceCarrierPosition = onGoToChoiceCarrierPosition,
            onReset = onReset
        )
    }
}

@Composable
fun AddCarrierCard(
    context: Context,
    selectedCarrier: String,
    selectedPosition: String,
    selectedDetailPosition: String,
    onGoToChoiceCarrierPosition: () -> Unit,
    onReset: () -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(LARGE_BUTTON_HEIGHT),
        colors = CardDefaults.cardColors(containerColor = WHITE),
        border = BorderStroke(1.dp, GRAY200),
        shape = RoundedCornerShape(LARGE_CORNER_SIZE),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp), // 좌우 패딩 추가
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // ✅ 클릭 이벤트를 감싸는 Box 추가
            Box(
                modifier = Modifier
                    .weight(1f) // 내부에서 가능한 모든 공간을 차지하도록 설정
                    .fillMaxHeight() // 높이를 최대로 설정
                    .noRippleClickable { onGoToChoiceCarrierPosition() }, // 클릭 이벤트
                contentAlignment = Alignment.Center // 내부 텍스트 중앙 정렬
            ) {
                TextComponent(
                    text = setAddCarrierCardText(
                        context = context,
                        carrier = selectedCarrier,
                        mainCarrier = selectedPosition,
                        detailCarrier = selectedDetailPosition
                    ),
                    textColor = GRAY500,
                    fontSize = B2
                )
            }

            // ✅ X 아이콘 (선택된 Carrier가 있을 때만 표시)
            if (selectedCarrier.isNotEmpty()) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_close2),
                    contentDescription = "Reset",
                    tint = GRAY500,
                    modifier = Modifier
                        .size(20.dp)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) { onReset() }
                )
            }
        }
    }

}

fun setAddCarrierCardText(context: Context, carrier: String, mainCarrier: String, detailCarrier: String): String{
    return if(carrier.isEmpty()) "+ ${context.getString(R.string.detail_carrier5)}" else "$carrier  |  $mainCarrier  |  $detailCarrier"
}

@Preview
@Composable
fun AddCarrierCardPreview() {
    AddCarrierCard(
        context = LocalContext.current,
        selectedPosition = "6년",
        selectedCarrier = "개발자",
        selectedDetailPosition = "Android",
        onGoToChoiceCarrierPosition = {},
        onReset = {}
    )
}

@Preview
@Composable
fun AddCarrierCardPreview2() {
    AddCarrierCard(
        context = LocalContext.current,
        selectedPosition = "",
        selectedCarrier = "",
        selectedDetailPosition = "",
        onGoToChoiceCarrierPosition = {},
        onReset = {}
    )
}