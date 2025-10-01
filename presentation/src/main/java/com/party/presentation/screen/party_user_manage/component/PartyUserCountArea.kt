package com.party.presentation.screen.party_user_manage.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.party.common.utils.WidthSpacer
import com.party.common.utils.fs
import com.party.guam.design.B2
import com.party.guam.design.GRAY500
import com.party.guam.design.PRIMARY
import com.party.guam.design.T1
import com.party.guam.design.T2
import com.party.guam.design.T3

@Composable
fun PartyUserCountArea(
    searchedListSize: Int,
    userListSize: Int,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = "파티원",
            fontWeight = FontWeight.Bold,
            fontSize = fs(T2)
        )
        WidthSpacer(6.dp)
        Text(
            text = searchedListSize.toString(),
            fontWeight = FontWeight.Bold,
            fontSize = fs(T1),
            color = PRIMARY
        )
        Text(
            text = "/",
            fontWeight = FontWeight.Bold,
            fontSize = fs(T1),
            color = GRAY500
        )
        Text(
            text = userListSize.toString(),
            fontWeight = FontWeight.Bold,
            fontSize = fs(T3),
            color = GRAY500
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PartyUserCountAreaPreview() {
    PartyUserCountArea(
        searchedListSize = 2,
        userListSize = 3
    )
}