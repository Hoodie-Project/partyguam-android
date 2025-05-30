package com.party.common.component.floating

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.party.common.utils.TextComponent
import com.party.guam.design.B1
import com.party.guam.design.BLACK
import com.party.guam.design.MEDIUM_CORNER_SIZE
import com.party.guam.design.PRIMARY
import com.party.guam.design.WHITE

@Composable
fun FabItem(
    title: String,
    onClicked: () -> Unit
) {
    Card(
        modifier = Modifier
            .width(170.dp)
            .height(56.dp),
        shape = RoundedCornerShape(MEDIUM_CORNER_SIZE),
        colors = CardDefaults.cardColors(
            containerColor = WHITE
        ),
        border = BorderStroke(1.dp, PRIMARY)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.CenterStart,
        ){
            TextComponent(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 20.dp),
                text = title,
                fontSize = B1,
                textColor = BLACK,
                onClick = { onClicked() }
            )
        }
    }
}