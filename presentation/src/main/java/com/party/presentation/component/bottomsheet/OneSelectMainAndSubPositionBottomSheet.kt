package com.party.presentation.component.bottomsheet

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
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.party.common.R
import com.party.common.component.bottomsheet.component.ApplyButton
import com.party.common.component.bottomsheet.component.BottomSheetTitleArea
import com.party.guam.design.B2
import com.party.guam.design.BLACK
import com.party.guam.design.GRAY100
import com.party.common.utils.HeightSpacer
import com.party.common.utils.WidthSpacer
import com.party.common.utils.fs
import com.party.domain.model.user.detail.PositionList
import com.party.presentation.screen.party_create.component.bottomsheet.PositionSelectArea

/*
    단일 선택 메인 포지션, 서브 포지션 바텀시트
* */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OneSelectMainAndSubPositionBottomSheet(
    bottomSheetTitle: String,
    selectedMainPosition: String,
    selectedSubPosition: PositionList,
    subPositionList: List<PositionList>,
    onModelClose: () -> Unit,
    buttonText: String = "선택 완료",
    onApply: (String, PositionList) -> Unit,
    onClickMainPosition: (String) -> Unit,
) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
    )
    
    val mainPosition = if(selectedMainPosition.isNotEmpty()) selectedMainPosition else "기획자"
    var inModalSelectedMainPosition by remember {
        mutableStateOf(mainPosition)
    }

    var inModalSelectedSubPosition by remember {
        mutableStateOf(selectedSubPosition)
    }
    
    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = { onModelClose() },
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
                    onSheetClose = onModelClose
                )

                PositionSelectArea(
                    subPositionList = subPositionList,
                    selectedMainPosition = inModalSelectedMainPosition,
                    selectedSubPosition = inModalSelectedSubPosition,
                    onMainPositionClick = {
                        onClickMainPosition(it)
                        inModalSelectedMainPosition = it
                        inModalSelectedSubPosition = PositionList(0, "", "")
                    },
                    onSelectSubPosition = {
                        inModalSelectedSubPosition = it
                    },
                )
            }

            if(inModalSelectedSubPosition.sub.isNotEmpty()){
                SelectedPositionArea(
                    main = inModalSelectedMainPosition,
                    sub = inModalSelectedSubPosition,
                    onDelete = {
                        inModalSelectedSubPosition = PositionList(0, "", "")
                        inModalSelectedMainPosition = ""
                    }
                )
            }

            ApplyButton(
                buttonText = buttonText,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                isActive = if(inModalSelectedMainPosition.isNotEmpty() && inModalSelectedSubPosition.sub.isNotEmpty()) true else false,
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
                fontSize = fs(B2),
            )
            WidthSpacer(widthDp = 4.dp)
            IconButton(
                modifier = Modifier.size(16.dp),
                onClick = { onDelete() }
            ) {
                Icon(
                    modifier = Modifier.size(16.dp),
                    painter = painterResource(id = R.drawable.icon_close2),
                    contentDescription = "close icon",
                    tint = Color.Gray
                )
            }
        }
    }
}

@Preview
@Composable
fun OneSelectMainAndSubPositionBottomSheetPreview() {
    OneSelectMainAndSubPositionBottomSheet(
        bottomSheetTitle = "ㅇㅇㅇ",
        selectedMainPosition = "개발자",
        selectedSubPosition = PositionList(id = 0, main = "개발자", sub = "Android"),
        onApply = { _, _ ->},
        onClickMainPosition = {},
        onModelClose = {},
        subPositionList = emptyList(),
        buttonText = "",
    )
}