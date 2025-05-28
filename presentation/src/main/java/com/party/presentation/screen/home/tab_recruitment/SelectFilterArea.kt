package com.party.presentation.screen.home.tab_recruitment

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.party.common.R
import com.party.common.component.chip.OrderByCreateDtChip
import com.party.guam.design.B2
import com.party.guam.design.BLACK
import com.party.guam.design.GRAY200
import com.party.guam.design.GRAY500
import com.party.guam.design.PRIMARY
import com.party.guam.design.WHITE
import com.party.common.utils.WidthSpacer

@Composable
fun SelectFilterArea(
    filterName1: String,
    filterName2: String,
    isPositionSheetOpen: Boolean,
    isPartyTypeSheetOpen: Boolean,
    isPositionFilterClick: (Boolean) -> Unit,
    isPartyTypeFilterClick: (Boolean) -> Unit,
    selectedCreateDataOrderByDesc: Boolean,
    onChangeOrderBy: (Boolean) -> Unit,
    selectedPositionNumber: Int,
    selectedPartyNumber: Int,
    isShowSelectedPartyNumber: Boolean,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(36.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier
                .wrapContentWidth()
                .fillMaxHeight(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            SelectFilterItem(
                isShowNumber = false,
                filterName = filterName1,
                isSheetOpen = isPositionSheetOpen,
                fontColor = if(selectedPositionNumber > 0) BLACK else GRAY500,
                borderColor = if(selectedPositionNumber > 0) PRIMARY else GRAY200,
                onClick = { isPositionFilterClick(it) },
                number = selectedPositionNumber,
            )
            WidthSpacer(widthDp = 8.dp)
            SelectFilterItem(
                isShowNumber = isShowSelectedPartyNumber,
                filterName = filterName2,
                isSheetOpen = isPartyTypeSheetOpen,
                fontColor = if(selectedPartyNumber > 0) BLACK else GRAY500,
                borderColor = if(selectedPartyNumber > 0) PRIMARY else GRAY200,
                onClick = { isPartyTypeFilterClick(it)},
                number = selectedPartyNumber,
            )
        }

        OrderByCreateDtChip(
            text = stringResource(id = R.string.filter1),
            orderByDesc = selectedCreateDataOrderByDesc,
            onChangeSelected = { onChangeOrderBy(it) }
        )
    }
}

@Composable
fun SelectFilterItem(
    isShowNumber: Boolean,
    filterName: String,
    isSheetOpen: Boolean,
    number: Int = 0,
    fontColor: Color = GRAY500,
    borderColor: Color = GRAY200,
    onClick: (Boolean) -> Unit,
) {
    Card(
        onClick = { onClick(!isSheetOpen) },
        modifier = Modifier
            .wrapContentWidth()
            .height(36.dp),
        colors = CardDefaults.cardColors(
            containerColor = WHITE,
        ),
        shape = RoundedCornerShape(999.dp),
        border = BorderStroke(1.dp, borderColor),
    ) {
        Row(
            modifier = Modifier
                .fillMaxHeight()
                .padding(horizontal = 12.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = filterName,
                color = fontColor,
                fontSize = B2
            )
            if(number > 0 && !isShowNumber){
                Text(
                    text = number.toString(),
                    color = PRIMARY,
                    fontSize = B2,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.offset(x = (2).dp, y = (1).dp),
                )
            }
            WidthSpacer(widthDp = 2.dp)
            Icon(
                modifier = Modifier.size(16.dp),
                painter = painterResource(id = R.drawable.icon_arrow_down),
                contentDescription = "Arrow Down",
                tint = fontColor,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SelectFilterAreaPreview() {
    SelectFilterArea(
        filterName1 = "직무",
        filterName2 = "파티유형",
        isPositionSheetOpen = false,
        isPartyTypeSheetOpen = false,
        isPositionFilterClick = {},
        isPartyTypeFilterClick = {},
        selectedCreateDataOrderByDesc = false,
        onChangeOrderBy = {},
        isShowSelectedPartyNumber = false,
        selectedPartyNumber = 3,
        selectedPositionNumber = 5,
    )
}

@Preview(showBackground = true)
@Composable
private fun SelectFilterItemPreview() {
    SelectFilterItem(
        isShowNumber = false,
        filterName = "직무",
        isSheetOpen = false,
        onClick = {},
    )
}

