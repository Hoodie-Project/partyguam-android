package com.party.presentation.screen.party_create.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.party.common.R
import com.party.common.component.icon.DrawableIconButton
import com.party.common.ui.theme.GRAY300
import com.party.common.ui.theme.LARGE_CORNER_SIZE

@Composable
fun PartyImageUploadArea(
    modifier: Modifier,
) {
    Box(
        modifier = modifier
            .width(220.dp)
            .height(170.dp),
        contentAlignment = Alignment.Center,
    ) {
        Card(
            modifier = Modifier
                .width(200.dp)
                .height(150.dp),
            shape = RoundedCornerShape(LARGE_CORNER_SIZE),
            colors = CardDefaults.cardColors(
                containerColor = GRAY300
            )
        ) {

        }

        DrawableIconButton(
            modifier = Modifier
                .size(32.dp)
                .align(Alignment.BottomEnd),
            icon = painterResource(id = R.drawable.select_image),
            contentDescription = "Select Image",
            onClick = {},
            iconColor = Color.Unspecified
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PartyImageUploadAreaPreview() {
    PartyImageUploadArea(
        modifier = Modifier
    )
}