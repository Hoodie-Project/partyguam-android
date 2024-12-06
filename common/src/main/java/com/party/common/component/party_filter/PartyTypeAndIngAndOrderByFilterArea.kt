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
import com.party.common.HeightSpacer
import com.party.common.R
import com.party.common.TextComponent
import com.party.common.WidthSpacer
import com.party.common.component.chip.OrderByCreateDtChip
import com.party.common.noRippleClickable
import com.party.common.ui.theme.B2
import com.party.common.ui.theme.GRAY200
import com.party.common.ui.theme.GRAY400
import com.party.common.ui.theme.GRAY500
import com.party.common.ui.theme.LARGE_CORNER_SIZE
import com.party.common.ui.theme.PRIMARY
import com.party.common.ui.theme.WHITE

@Composable
fun PartyTypeAndIngAndOrderByFilterArea(
    checked: Boolean,
    onToggle: (Boolean) -> Unit,
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
                checked = checked,
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
                fontSize = B2
            )
            if(number > 0){
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
                painter = painterResource(id = R.drawable.arrow_down_icon),
                contentDescription = "Arrow Down",
                tint = GRAY400,
            )
        }
    }
}

@Composable
private fun IngToggle(
    checked: Boolean,
    onToggle: (Boolean) -> Unit,
) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
    ){
        TextComponent(
            text = "진행중",
            textColor = if (checked) PRIMARY else GRAY500,
            fontSize = B2,
        )

        WidthSpacer(widthDp = 2.dp)
        Image(
            painter = if (checked) painterResource(id = R.drawable.toggle_on) else painterResource(id = R.drawable.toggle_off),
            contentDescription = "toggle",
            modifier = Modifier.noRippleClickable {
                onToggle(!checked)
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
        checked = true,
        onToggle = {},
        isPartyTypeFilterClick = {},
        isDescParty = true,
        onChangeOrderBy = {}
    )
}