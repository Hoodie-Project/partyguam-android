package com.party.presentation.screen.party_create.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.party.common.R
import com.party.common.WidthSpacer
import com.party.presentation.component.bottomsheet.OneSelectMainAndSubPositionBottomSheet
import com.party.common.component.icon.DrawableIconButton
import com.party.common.component.input_field.InputField
import com.party.common.ui.theme.BLACK
import com.party.common.ui.theme.GRAY200
import com.party.common.ui.theme.GRAY400
import com.party.common.ui.theme.PRIMARY
import com.party.domain.model.user.detail.PositionList

@Composable
fun PartyCreateSelectPositionArea(
    subPositionList: List<PositionList>,
    isMainPositionBottomSheetShow: Boolean,
    selectedMainPosition: String,
    selectedSubPosition: PositionList,
    onApply: (String, PositionList) -> Unit,
    onShowPositionBottomSheet: (Boolean) -> Unit,
    onClickMainPosition: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        InputField(
            modifier = Modifier
                .weight(1f),
            inputText = selectedMainPosition,
            inputTextColor = if(selectedMainPosition.isEmpty())GRAY400 else BLACK,
            borderColor = if(selectedMainPosition.isEmpty()) GRAY200 else PRIMARY,
            elevation = if(selectedMainPosition.isEmpty()) 4.dp else 0.dp,
            readOnly = true,
            placeHolder = "직무",
            onValueChange = {},
            icon = {
                DrawableIconButton(
                    icon = painterResource(id = R.drawable.icon_arrow_down),
                    contentDescription = "arrow_down",
                    onClick = { onShowPositionBottomSheet(true) }
                )
            }
        )
        WidthSpacer(widthDp = 12.dp)
        InputField(
            modifier = Modifier
                .weight(1f),
            inputText = selectedSubPosition.sub,
            inputTextColor = if(selectedSubPosition.sub.isEmpty())GRAY400 else BLACK,
            borderColor = if(selectedSubPosition.sub.isEmpty())GRAY200 else PRIMARY,
            elevation = if(selectedSubPosition.sub.isEmpty()) 4.dp else 0.dp,
            readOnly = true,
            placeHolder = "직군",
            onValueChange = {},
            icon = {
                DrawableIconButton(
                    icon = painterResource(id = R.drawable.icon_arrow_down),
                    contentDescription = "arrow_down",
                    onClick = { onShowPositionBottomSheet(true) }
                )
            }
        )
    }

    if(isMainPositionBottomSheetShow){
        OneSelectMainAndSubPositionBottomSheet(
            subPositionList = subPositionList,
            bottomSheetTitle = "모집 포지션",
            selectedMainPosition = selectedMainPosition,
            selectedSubPosition = selectedSubPosition,
            onModelClose = { onShowPositionBottomSheet(false) },
            onApply = onApply,
            onClickMainPosition = onClickMainPosition
        )
    }
}