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
import com.party.common.TextComponent
import com.party.common.WidthSpacer
import com.party.common.noRippleClickable
import com.party.common.ui.theme.B2
import com.party.common.ui.theme.GRAY500
import com.party.common.ui.theme.PRIMARY
import com.party.presentation.screen.home.tab_recruitment.SelectFilterItem

@Composable
fun FilterArea(
    checked: Boolean,
    onToggle: (Boolean) -> Unit,
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
            filterName = "파티유형",
            isSheetOpen = false,
            onClick = {
                isPartyTypeFilterClick(it)
            }
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
            painter = if (checked) painterResource(id = R.drawable.toggle_on) else painterResource(id = R.drawable.toggle_off),
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
        checked = true,
        onToggle = {},
        isPartyTypeFilterClick = {}
    )
}