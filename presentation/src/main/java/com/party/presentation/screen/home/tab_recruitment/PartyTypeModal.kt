package com.party.presentation.screen.home.tab_recruitment

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.party.common.R
import com.party.common.utils.WidthSpacer
import com.party.common.component.bottomsheet.list.partyTypeList2
import com.party.common.utils.noRippleClickable
import com.party.common.ui.theme.B1
import com.party.common.ui.theme.BLACK
import com.party.common.ui.theme.PRIMARY
import com.party.common.ui.theme.WHITE

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PartyTypeModal(
    titleText: String,
    selectedPartyType: List<String>,
    onClick: (String) -> Unit,
    onModelClose: () -> Unit,
    onReset: () -> Unit,
    onApply: () -> Unit,
) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = {
            onModelClose()
        },
        containerColor = Color.White,
        dragHandle = null,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(20.dp)
        ) {
            ModalTitleArea(
                titleText = titleText,
                onModelClose = onModelClose,
            )
            ModelCheckListArea(
                selectedPartyType = selectedPartyType,
                onClick = onClick,
            )
            ModalBottomArea(
                buttonText1 = stringResource(id = R.string.recruitment_modal1),
                buttonTextColor1 = BLACK,
                buttonContainerColor1 = WHITE,
                buttonBorderColor1 = PRIMARY,
                buttonText2 = stringResource(id = R.string.recruitment_modal2),
                buttonTextColor2 = BLACK,
                buttonContainerColor2 = PRIMARY,
                buttonBorderColor2 = PRIMARY,
                onReset = onReset,
                onApply = onApply,
            )
        }
    }
}

@Composable
private fun ModelCheckListArea(
    selectedPartyType: List<String>,
    onClick: (String) -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        itemsIndexed(
            items = partyTypeList2,
            key = { index, _ ->
                index
            }
        ) { _, item ->
            ModalCheckListItem(
                text = item,
                selectedPartyType = selectedPartyType,
                onClick = {onClick(it)}
            )
        }
    }
}

@Composable
fun ModalCheckListItem(
    text: Pair<String, Int>,
    selectedPartyType: List<String>,
    onClick: (String) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp)
            .noRippleClickable {
                onClick(text.first)
            },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            modifier = Modifier.size(20.dp),
            painter = if(selectedPartyType.contains(text.first)) painterResource(id = R.drawable.icon_checed) else painterResource(id = R.drawable.icon_uncheckd),
            contentDescription = "check",
        )

        WidthSpacer(widthDp = 6.dp)
        Text(
            text = text.first,
            fontWeight = if(selectedPartyType.contains(text.first)) FontWeight.Bold else FontWeight.Normal,
            fontSize = B1,
        )
    }
}