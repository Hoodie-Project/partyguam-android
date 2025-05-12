package com.party.presentation.screen.party_edit_recruitment.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.party.common.utils.HeightSpacer
import com.party.common.ui.theme.B2
import com.party.common.ui.theme.PRIMARY
import com.party.common.ui.theme.WHITE

@Composable
fun PartyRecruitmentEditFloatingArea(
    modifier: Modifier,
    onClick: () -> Unit,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.End,
    ) {
        FloatingActionButton(
            modifier = Modifier,
            onClick = onClick,
            shape = CircleShape,
            containerColor = PRIMARY,
            contentColor = WHITE
        ) {
            Column(
                modifier = Modifier
                    .width(48.dp)
                    .height(80.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "모집",
                    fontWeight = FontWeight.Bold,
                    fontSize = B2
                )
                HeightSpacer(4.dp)
                Icon(
                    modifier = Modifier.size(32.dp),
                    imageVector = Icons.Rounded.Add,
                    tint = WHITE,
                    contentDescription = "This is Expand Button",
                )
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun PartyRecruitmentEditFloatingAreaPreview() {
    PartyRecruitmentEditFloatingArea(
        modifier = Modifier,
        onClick = {}
    )
}