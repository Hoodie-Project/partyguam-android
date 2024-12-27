package com.party.presentation.screen.profile_edit_career.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.party.common.HeightSpacer
import com.party.common.TextComponent
import com.party.common.component.bottomsheet.list.positionList
import com.party.common.noRippleClickable
import com.party.common.ui.theme.GRAY200
import com.party.common.ui.theme.LARGE_BUTTON_HEIGHT
import com.party.common.ui.theme.LARGE_CORNER_SIZE
import com.party.common.ui.theme.LIGHT100
import com.party.common.ui.theme.LIGHT300
import com.party.common.ui.theme.T3
import com.party.common.ui.theme.WHITE

@Composable
fun ProfileEditSelectPositionArea(
    selectedMainPosition: String,
    onSelectMainPosition: (String) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        TextComponent(
            text = "포지션",
            fontSize = T3,
            fontWeight = FontWeight.SemiBold,
        )
        HeightSpacer(heightDp = 12.dp)

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            itemsIndexed(
                items = positionList,
                key = { index, _ ->
                    index
                }
            ) { _, item ->
                SelectPositionItem(
                    text = item,
                    fontWeight = if(selectedMainPosition == item) FontWeight.SemiBold else FontWeight.Normal,
                    containerColor = if(selectedMainPosition == item) LIGHT300 else WHITE,
                    borderColor = if(selectedMainPosition == item) LIGHT100 else GRAY200,
                    onSelect = onSelectMainPosition
                )
            }
        }
    }
}

@Composable
private fun SelectPositionItem(
    text: String,
    fontWeight: FontWeight,
    containerColor: Color,
    borderColor: Color,
    onSelect: (String) -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(LARGE_BUTTON_HEIGHT)
            .noRippleClickable { onSelect(text) },
        shape = RoundedCornerShape(LARGE_CORNER_SIZE),
        border = BorderStroke(1.dp, borderColor),
        colors = CardDefaults.cardColors(
            containerColor = containerColor,
        ),
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ){
            Text(
                text = text,
                fontSize = T3,
                fontWeight = fontWeight,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ProfileEditSelectPositionAreaPreview() {
    ProfileEditSelectPositionArea(
        selectedMainPosition = "포지션",
        onSelectMainPosition = { }
    )
}