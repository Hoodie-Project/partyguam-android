package com.party.presentation.screen.party_create.component.bottomsheet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.party.common.HeightSpacer
import com.party.common.R
import com.party.common.WidthSpacer
import com.party.common.component.bottomsheet.component.ApplyButton
import com.party.common.component.bottomsheet.component.BottomSheetTitleArea
import com.party.common.ui.theme.B2
import com.party.common.ui.theme.BLACK
import com.party.common.ui.theme.GRAY100
import com.party.domain.model.user.detail.PositionList
import com.party.presentation.screen.home.viewmodel.HomeViewModel

/*
    단일 선택 메인 포지션, 서브 포지션 바텀시트
* */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OneSelectMainAndSubPositionBottomSheet(
    snackBarHostState: SnackbarHostState,
    bottomSheetTitle: String,
    selectedMainPosition: String,
    selectedSubPosition: PositionList,
    homeViewModel: HomeViewModel,
    onModelClose: () -> Unit,
    onApply: (String, PositionList) -> Unit,
) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    var inModalSelectedMainPosition by remember {
        mutableStateOf(selectedMainPosition)
    }

    var inModalSelectedSubPosition by remember {
        mutableStateOf(selectedSubPosition)
    }

    LaunchedEffect(key1 = inModalSelectedMainPosition) {
        homeViewModel.getSubPositionList(main = inModalSelectedMainPosition)
    }

    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = {
            onModelClose()
        },
        containerColor = White,
        dragHandle = null,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.8f)
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                BottomSheetTitleArea(
                    titleText = bottomSheetTitle,
                    onModelClose = onModelClose
                )

                PositionSelectArea(
                    homeViewModel = homeViewModel,
                    selectedMainPosition = inModalSelectedMainPosition,
                    selectedSubPosition = inModalSelectedSubPosition,
                    onMainPositionClick = {
                        inModalSelectedMainPosition = it
                        inModalSelectedSubPosition = PositionList(0, "", "")
                    },
                    onSelectSubPosition = {
                        inModalSelectedSubPosition = it
                    },
                    snackBarHostState = snackBarHostState
                )
            }

            if(inModalSelectedSubPosition.sub.isNotEmpty()){
                SelectedPositionArea(
                    main = inModalSelectedMainPosition,
                    sub = inModalSelectedSubPosition,
                    onDelete = {
                        inModalSelectedSubPosition = PositionList(0, "", "")
                    }
                )
            }

            ApplyButton(
                buttonText = "선택 완료",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                onClick = {
                    onApply(inModalSelectedMainPosition, inModalSelectedSubPosition)
                    onModelClose()
                }
            )
        }
    }
}

@Composable
private fun SelectedPositionArea(
    main: String,
    sub: PositionList,
    onDelete: () -> Unit,
) {
    HorizontalDivider(
        modifier = Modifier.padding(vertical = 10.dp)
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
    ) {
        SelectedPositionItem(
            main = main,
            sub = sub,
            onDelete = onDelete
        )
    }

    HeightSpacer(heightDp = 8.dp)
}

@Composable
private fun SelectedPositionItem(
    main: String,
    sub: PositionList,
    onDelete: () -> Unit,
){
    Card(
        onClick = { onDelete() },
        modifier = Modifier
            .wrapContentWidth()
            .height(32.dp),
        shape = RoundedCornerShape(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = GRAY100,
        )
    ) {
        Row(
            modifier = Modifier
                .wrapContentWidth()
                .fillMaxHeight()
                .padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = "$main | ${sub.sub}",
                color = BLACK,
                fontSize = B2,
            )
            WidthSpacer(widthDp = 4.dp)
            IconButton(
                modifier = Modifier.size(16.dp),
                onClick = { onDelete() }
            ) {
                Icon(
                    modifier = Modifier.size(16.dp),
                    painter = painterResource(id = R.drawable.close2),
                    contentDescription = "close icon",
                    tint = Color.Gray
                )
            }
        }
    }
}