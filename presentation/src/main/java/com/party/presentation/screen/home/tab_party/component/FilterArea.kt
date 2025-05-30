package com.party.presentation.screen.home.tab_party.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.party.common.R
import com.party.guam.design.B2
import com.party.guam.design.BLACK
import com.party.guam.design.GRAY200
import com.party.guam.design.GRAY500
import com.party.guam.design.PRIMARY
import com.party.common.utils.TextComponent
import com.party.common.utils.WidthSpacer
import com.party.common.utils.noRippleClickable
import com.party.presentation.screen.home.tab_recruitment.SelectFilterItem

@Composable
fun FilterArea(
    isShowNumber: Boolean,
    checked: Boolean,
    onToggle: (Boolean) -> Unit,
    number: Int,
    isPartyTypeFilterClick: (Boolean) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(68.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        SelectFilterItem(
            isShowNumber = isShowNumber,
            filterName = "파티유형",
            isSheetOpen = false,
            number = number,
            fontColor = if(number > 0) BLACK else GRAY500,
            borderColor = if(number > 0) PRIMARY else GRAY200,
            onClick = { isPartyTypeFilterClick(it) }
        )

        IngToggle(
            checked = checked,
            onToggle = onToggle
        )
    }
}

@Composable
fun IngToggle(
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
            painter = if (checked) painterResource(id = R.drawable.icon_toggle_on) else painterResource(id = R.drawable.icon_toggle_off),
            contentDescription = "toggle",
            modifier = Modifier.noRippleClickable {
                onToggle(!checked)
            }
        )
    }
}

@Preview
@Composable
private fun FilterAreaPreview() {
    FilterArea(
        isShowNumber = false,
        checked = true,
        onToggle = {},
        number = 2,
        isPartyTypeFilterClick = {}
    )
}