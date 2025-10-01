package com.party.common.component.party_filter

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.party.common.utils.HeightSpacer
import com.party.common.R
import com.party.common.utils.TextComponent
import com.party.common.utils.WidthSpacer
import com.party.common.component.chip.OrderByCreateDtChip
import com.party.common.utils.fs
import com.party.common.utils.noRippleClickable
import com.party.guam.design.B2
import com.party.guam.design.GRAY200
import com.party.guam.design.GRAY400
import com.party.guam.design.GRAY500
import com.party.guam.design.LARGE_CORNER_SIZE
import com.party.guam.design.PRIMARY
import com.party.guam.design.WHITE

@Composable
fun PartyTypeAndIngAndOrderByFilterArea(
    isActiveParty: String,
    onToggle: (String) -> Unit,
    isPartyTypeFilterClick: (Boolean) -> Unit,
    isDescParty: Boolean,
    onChangeOrderBy: (Boolean) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(68.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            SelectFilterItem(
                filterName = "파티유형",
                isSheetOpen = false,
                onClick = { isPartyTypeFilterClick(it) }
            )

            IngToggle(
                isActiveParty = isActiveParty,
                onToggle = onToggle
            )
        }

        HeightSpacer(heightDp = 8.dp)
        FilterDate(
            selectedCreateDataOrderByDesc = isDescParty,
            onChangeOrderBy = onChangeOrderBy
        )
    }
}

@Composable
private fun SelectFilterItem(
    filterName: String,
    isSheetOpen: Boolean,
    number: Int = 0,
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
                fontSize = fs(B2)
            )
            if(number > 0){
                Text(
                    text = number.toString(),
                    color = PRIMARY,
                    fontSize = fs(B2),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.offset(x = (2).dp, y = (1).dp),
                )
            }
            WidthSpacer(widthDp = 2.dp)
            Icon(
                modifier = Modifier.size(16.dp),
                painter = painterResource(id = R.drawable.icon_arrow_down),
                contentDescription = "Arrow Down",
                tint = GRAY400,
            )
        }
    }
}

@Composable
private fun IngToggle(
    isActiveParty: String,
    onToggle: (String) -> Unit,
) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
    ){
        TextComponent(
            text = "진행중",
            textColor = if (isActiveParty == "active") PRIMARY else GRAY500,
            fontSize = B2,
        )

        WidthSpacer(widthDp = 2.dp)
        Image(
            painter = if (isActiveParty == "active") painterResource(id = R.drawable.icon_toggle_on) else painterResource(id = R.drawable.icon_toggle_off),
            contentDescription = "toggle",
            modifier = Modifier.noRippleClickable {
                if (isActiveParty == "active") { onToggle("archived") } else { onToggle("active") }
            }
        )
    }
}

@Composable
fun FilterDate(
    selectedCreateDataOrderByDesc: Boolean,
    onChangeOrderBy: (Boolean) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.End
    ) {
        OrderByCreateDtChip(
            text = stringResource(id = R.string.filter1),
            orderByDesc = selectedCreateDataOrderByDesc,
            onChangeSelected = { onChangeOrderBy(it) }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PartyTypeAndIngAndOrderByFilterAreaPreview() {
    PartyTypeAndIngAndOrderByFilterArea(
        onToggle = {},
        isPartyTypeFilterClick = {},
        isDescParty = true,
        onChangeOrderBy = {},
        isActiveParty = "active"
    )
}