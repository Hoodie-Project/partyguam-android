package com.party.presentation.screen.profile_edit_career.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.party.common.R
import com.party.common.component.icon.DrawableIcon
import com.party.common.component.icon.DrawableIconButton
import com.party.common.ui.theme.B2
import com.party.common.ui.theme.BLACK
import com.party.common.ui.theme.GRAY200
import com.party.common.ui.theme.GRAY500
import com.party.common.ui.theme.LARGE_BUTTON_HEIGHT
import com.party.common.ui.theme.LARGE_CORNER_SIZE
import com.party.common.ui.theme.T3
import com.party.common.ui.theme.WHITE
import com.party.common.utils.HeightSpacer
import com.party.common.utils.TextComponent
import com.party.common.utils.WidthSpacer
import com.party.common.utils.noRippleClickable
import com.party.presentation.screen.profile_edit_career.ProfileEditCareerState

@Composable
fun ProfileEditCareerSelectedPositionArea(
    state: ProfileEditCareerState,
    onGoToSelectCareerAndPosition: (Boolean) -> Unit,
    onResetSelectedFirst: () -> Unit,
    onResetSelectedSecond: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        val mainPositionYears = state.getMainPosition?.years
            ?.takeIf { it != 0 }
            ?.let { "${it}년" } ?: "신입"
        ProfileEditCareerSelectedPositionItem(
            selectedCareer = mainPositionYears,
            selectedMainPosition = state.getMainPosition?.position?.main ?: "",
            selectedSubPosition = state.getMainPosition?.position?.sub ?: "",
            title = "주포지션",
            onGoToSelectCareerAndPosition = { onGoToSelectCareerAndPosition(true) },
            onReset = onResetSelectedFirst
        )

        HeightSpacer(heightDp = 40.dp)

        val subPositionYears = state.getSubPosition?.years
            ?.takeIf { it != 0 }
            ?.let { "${it}년" } ?: "신입"
        ProfileEditCareerSelectedPositionItem(
            selectedCareer = subPositionYears,
            selectedMainPosition = state.getSubPosition?.position?.main ?: "",
            selectedSubPosition = state.getSubPosition?.position?.sub ?: "",
            title = "부포지션",
            onGoToSelectCareerAndPosition = { onGoToSelectCareerAndPosition(false) },
            onReset = onResetSelectedSecond
        )
    }
}

@Composable
private fun ProfileEditCareerSelectedPositionItem(
    selectedCareer: String,
    selectedMainPosition: String,
    selectedSubPosition: String,
    title: String,
    onGoToSelectCareerAndPosition: () -> Unit,
    onReset: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        TextComponent(
            text = title,
            fontSize = T3,
            fontWeight = FontWeight.Bold,
        )
        HeightSpacer(heightDp = 12.dp)

        AddCarrierCard(
            selectedCareer = selectedCareer,
            selectedMainPosition = selectedMainPosition,
            selectedSubPosition = selectedSubPosition,
            onGoToSelectCareerAndPosition = onGoToSelectCareerAndPosition,
            onReset = onReset
        )
    }
}

@Composable
private fun AddCarrierCard(
    selectedCareer: String,
    selectedMainPosition: String,
    selectedSubPosition: String,
    onGoToSelectCareerAndPosition: () -> Unit,
    onReset: () -> Unit,
) {
    Card(
        onClick = onGoToSelectCareerAndPosition,
        modifier = Modifier
            .fillMaxWidth()
            .height(LARGE_BUTTON_HEIGHT)
        ,
        colors = CardDefaults.cardColors(
            containerColor = WHITE
        ),
        border = BorderStroke(1.dp, GRAY200),
        shape = RoundedCornerShape(LARGE_CORNER_SIZE),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        if(selectedCareer.isNotEmpty() && selectedMainPosition.isNotEmpty() && selectedSubPosition.isNotEmpty()){
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.CenterStart
            ){
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    TextComponent(
                        text = "$selectedCareer  |  $selectedMainPosition  |  $selectedSubPosition",
                        fontSize = B2,
                        textColor = BLACK,
                        onClick = onGoToSelectCareerAndPosition
                    )

                    DrawableIconButton(
                        icon = painterResource(id = R.drawable.icon_close2),
                        contentDescription = "close",
                        onClick = onReset
                    )
                }
            }
        }else {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    DrawableIcon(
                        modifier = Modifier
                            .size(12.dp),
                        icon = painterResource(id = R.drawable.icon_add),
                        contentDescription = "add",
                    )
                    WidthSpacer(widthDp = 2.dp)
                    TextComponent(
                        text = "추가하기",
                        fontSize = B2,
                        textColor = GRAY500,
                        onClick = onGoToSelectCareerAndPosition,
                    )
                }
            }
        }
    }
}