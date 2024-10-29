package com.party.presentation.screen.home.tab_recruitment

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.party.common.R
import com.party.common.WidthSpacer
import com.party.common.noRippleClickable
import com.party.common.ui.theme.B2
import com.party.common.ui.theme.BLACK
import com.party.common.ui.theme.GRAY200
import com.party.common.ui.theme.GRAY400
import com.party.common.ui.theme.GRAY500
import com.party.common.ui.theme.LARGE_CORNER_SIZE
import com.party.common.ui.theme.WHITE

@Composable
fun SelectFilterArea(
    filterName1: String,
    filterName2: String,
    filterName3: String,
    isPositionSheetOpen: Boolean,
    isPartyTypeSheetOpen: Boolean,
    isPositionFilterClick: (Boolean) -> Unit,
    isPartyTypeFilterClick: (Boolean) -> Unit,
    selectedCreateDataOrderByDesc: Boolean,
    onChangeOrderBy: (Boolean) -> Unit,
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
                filterName = filterName1,
                isSheetOpen = isPositionSheetOpen,
                onClick = { isPositionFilterClick(it) }
            )
            WidthSpacer(widthDp = 8.dp)
            SelectFilterItem(
                filterName = filterName2,
                isSheetOpen = isPartyTypeSheetOpen,
                onClick = { isPartyTypeFilterClick(it)}
            )
        }

        Row(
            modifier = Modifier
                .wrapContentWidth()
                .fillMaxHeight()
                .noRippleClickable {
                    onChangeOrderBy(!selectedCreateDataOrderByDesc)
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = filterName3,
                color = BLACK,
                fontSize = B2
            )
            Icon(
                modifier = Modifier.size(16.dp),
                imageVector = if(selectedCreateDataOrderByDesc) Icons.Default.ArrowDownward else Icons.Default.ArrowUpward,
                contentDescription = "Arrow",
            )
        }
    }
}

@Composable
fun SelectFilterItem(
    filterName: String,
    isSheetOpen: Boolean,
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
        shape = RoundedCornerShape(LARGE_CORNER_SIZE),
        border = BorderStroke(1.dp, GRAY200),
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
                color = GRAY500,
                fontSize = B2
            )
            WidthSpacer(widthDp = 2.dp)
            Icon(
                modifier = Modifier.size(16.dp),
                painter = painterResource(id = R.drawable.arrow_down_icon),
                contentDescription = "Arrow Down",
                tint = GRAY400,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SelectFilterAreaPreview() {
    SelectFilterArea(
        filterName1 = "직무1",
        filterName2 = "파티유형",
        filterName3 = "등록일순",
        isPositionSheetOpen = false,
        isPartyTypeSheetOpen = false,
        isPositionFilterClick = {},
        isPartyTypeFilterClick = {},
        selectedCreateDataOrderByDesc = false,
        onChangeOrderBy = {},
    )
}

@Preview(showBackground = true)
@Composable
fun SelectFilterItemPreview() {
    SelectFilterItem(
        filterName = "직무",
        isSheetOpen = false,
        onClick = {},
    )
}

