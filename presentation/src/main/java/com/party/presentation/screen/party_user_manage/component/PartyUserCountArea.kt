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
import com.party.common.WidthSpacer
import com.party.common.ui.theme.GRAY400
import com.party.common.ui.theme.GRAY500
import com.party.common.ui.theme.PRIMARY
import com.party.common.ui.theme.T1
import com.party.common.ui.theme.T2
import com.party.common.ui.theme.T3

@Composable
fun PartyUserCountArea(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = "파티원",
            fontWeight = FontWeight.Bold,
            fontSize = T2
        )
        WidthSpacer(6.dp)
        Text(
            text = "1",
            fontWeight = FontWeight.Bold,
            fontSize = T1,
            color = PRIMARY
        )
        Text(
            text = "/",
            fontWeight = FontWeight.Bold,
            fontSize = T3,
            color = GRAY500
        )
        Text(
            text = "5",
            fontWeight = FontWeight.Bold,
            fontSize = T3,
            color = GRAY500
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PartyUserCountAreaPreview() {
    PartyUserCountArea()
}