package com.party.presentation.screen.home_detail_profile.component

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.party.common.R
import com.party.common.component.icon.DrawableIcon
import com.party.common.utils.HeightSpacer
import com.party.common.utils.TextComponent
import com.party.common.utils.WidthSpacer
import com.party.common.utils.noRippleClickable
import com.party.guam.design.B2
import com.party.guam.design.BLACK
import com.party.guam.design.GRAY200
import com.party.guam.design.GRAY300
import com.party.guam.design.GRAY500
import com.party.guam.design.LARGE_BUTTON_HEIGHT
import com.party.guam.design.LARGE_CORNER_SIZE
import com.party.guam.design.T3
import com.party.guam.design.WHITE
import com.party.presentation.screen.home_detail_profile.state.HomeDetailProfileState

@Composable
fun PositionSection(
    state: HomeDetailProfileState,
    onGoToChoiceCarrierPosition: (Boolean) -> Unit,
    onFirstReset: () -> Unit,
    onSecondReset: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        PositionAreaItem(
            title = stringResource(id = R.string.detail_carrier3),
            selectedCarrier = state.firstCareer,
            selectedPosition = state.firstMainPosition,
            selectedDetailPosition = state.firstSubPosition,
            onGoToChoiceCarrierPosition = { onGoToChoiceCarrierPosition(true) },
            onReset = onFirstReset
        )
        HeightSpacer(heightDp = 40.dp)
        PositionAreaItem(
            title = stringResource(id = R.string.detail_carrier4),
            selectedCarrier = state.secondCareer,
            selectedPosition = state.secondMainPosition,
            selectedDetailPosition = state.secondSubPosition,
            onGoToChoiceCarrierPosition = { onGoToChoiceCarrierPosition(false) },
            onReset = onSecondReset
        )
    }
}

@Composable
private fun PositionAreaItem(
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
            fontWeight = FontWeight.SemiBold,
            onClick = { onGoToChoiceCarrierPosition() }
        )
        HeightSpacer(heightDp = 12.dp)
        AddCarrierCard(
            selectedCarrier = selectedCarrier,
            selectedPosition = selectedPosition,
            selectedDetailPosition = selectedDetailPosition,
            onGoToChoiceCarrierPosition = onGoToChoiceCarrierPosition,
            onReset = onReset
        )
    }
}

@Composable
private fun AddCarrierCard(
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
                contentAlignment = if(selectedCarrier.isEmpty()) Alignment.Center else Alignment.CenterStart
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = if(selectedCarrier.isEmpty()) Arrangement.Center else Arrangement.Start
                ) {
                    if(selectedCarrier.isEmpty()){
                        DrawableIcon(
                            icon = painterResource(R.drawable.icon_add),
                            contentDescription = "",
                            tintColor = GRAY500,
                            modifier = Modifier.size(12.dp)
                        )
                        WidthSpacer(2.dp)
                        TextComponent(
                            text = "추가하기",
                            textColor = GRAY500,
                            fontSize = B2,
                            onClick = onGoToChoiceCarrierPosition
                        )
                    }else {
                        TextComponent(
                            text = selectedCarrier,
                            textColor = BLACK,
                            fontSize = B2,
                        )
                        WidthSpacer(12.dp)
                        DrawableIcon(
                            icon = painterResource(R.drawable.vertical_rectangle),
                            contentDescription = "",
                            tintColor = GRAY300,
                            modifier = Modifier
                                .width(2.dp)
                                .height(12.dp)
                        )
                        WidthSpacer(12.dp)
                        TextComponent(
                            text = selectedPosition,
                            textColor = BLACK,
                            fontSize = B2,
                        )
                        WidthSpacer(12.dp)
                        DrawableIcon(
                            icon = painterResource(R.drawable.vertical_rectangle),
                            contentDescription = "",
                            tintColor = GRAY300,
                            modifier = Modifier
                                .width(2.dp)
                                .height(12.dp)
                        )
                        WidthSpacer(12.dp)
                        TextComponent(
                            text = selectedDetailPosition,
                            textColor = BLACK,
                            fontSize = B2,
                        )
                    }

                }
            }

            // X 아이콘 (선택된 Carrier가 있을 때만 표시)
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