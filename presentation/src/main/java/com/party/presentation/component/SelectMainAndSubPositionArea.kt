package com.party.presentation.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.party.common.R
import com.party.common.WidthSpacer
import com.party.common.component.icon.DrawableIconButton
import com.party.common.component.input_field.InputField
import com.party.common.ui.theme.BLACK
import com.party.common.ui.theme.GRAY200
import com.party.common.ui.theme.GRAY400
import com.party.common.ui.theme.PRIMARY
import com.party.domain.model.user.detail.PositionList
import com.party.presentation.component.bottomsheet.OneSelectMainAndSubPositionBottomSheet
import com.party.presentation.screen.home.viewmodel.HomeViewModel

@Composable
fun SelectMainAndSubPositionArea(
    snackBarHostState: SnackbarHostState,
    homeViewModel: HomeViewModel,
    selectedMainPosition: String,
    selectedSubPosition: PositionList,
    onApply: (String, PositionList) -> Unit,
) {
    var isMainPositionBottomSheetShow by remember {
        mutableStateOf(false)
    }

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        InputField(
            modifier = Modifier
                .weight(1f),
            inputText = selectedMainPosition,
            inputTextColor = if(selectedMainPosition.isEmpty()) GRAY400 else BLACK,
            borderColor = if(selectedMainPosition.isEmpty()) GRAY200 else PRIMARY,
            elevation = if(selectedMainPosition.isEmpty()) 4.dp else 0.dp,
            readOnly = true,
            placeHolder = "직무",
            onValueChange = {},
            icon = {
                DrawableIconButton(
                    icon = painterResource(id = R.drawable.arrow_down_icon),
                    contentDescription = "arrow_down",
                    onClick = { isMainPositionBottomSheetShow = true}
                )
            }
        )
        WidthSpacer(widthDp = 12.dp)
        InputField(
            modifier = Modifier
                .weight(1f),
            inputText = selectedSubPosition.sub,
            inputTextColor = if(selectedSubPosition.sub.isEmpty()) GRAY400 else BLACK,
            borderColor = if(selectedSubPosition.sub.isEmpty()) GRAY200 else PRIMARY,
            elevation = if(selectedSubPosition.sub.isEmpty()) 4.dp else 0.dp,
            readOnly = true,
            placeHolder = "직군",
            onValueChange = {},
            icon = {
                DrawableIconButton(
                    icon = painterResource(id = R.drawable.arrow_down_icon),
                    contentDescription = "arrow_down",
                    onClick = { isMainPositionBottomSheetShow = true}
                )
            }
        )
    }

    if(isMainPositionBottomSheetShow){
        OneSelectMainAndSubPositionBottomSheet(
            snackBarHostState = snackBarHostState,
            homeViewModel = homeViewModel,
            bottomSheetTitle = "모집 포지션",
            selectedMainPosition = selectedMainPosition,
            selectedSubPosition = selectedSubPosition,
            onModelClose = {
                isMainPositionBottomSheetShow = false
            },
            onApply = onApply
        )
    }
}