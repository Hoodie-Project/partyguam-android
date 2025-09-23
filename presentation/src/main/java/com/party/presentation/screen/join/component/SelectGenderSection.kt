package com.party.presentation.screen.join.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.party.common.utils.WidthSpacer
import com.party.common.utils.noRippleClickable
import com.party.guam.design.BLACK
import com.party.guam.design.GRAY200
import com.party.guam.design.LARGE_CORNER_SIZE
import com.party.guam.design.LIGHT100
import com.party.guam.design.LIGHT300
import com.party.guam.design.T3
import com.party.guam.design.WHITE

@Composable
fun SelectGenderSection(
    selectedGender: String,
    onSelect: (String) -> Unit,
){
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        GenderCard(
            modifier = Modifier
                .weight(0.5f)
                .aspectRatio(1f),
            text = "남자",
            containerColor = if(selectedGender == "남자") LIGHT300 else WHITE,
            borderColor = if(selectedGender == "남자") LIGHT100 else GRAY200,
            fontWeight = if(selectedGender == "남자") FontWeight.SemiBold else FontWeight.Normal,
            onSelect = {
                onSelect("남자")
            }
        )

        WidthSpacer(widthDp = 8.dp)

        GenderCard(
            modifier = Modifier
                .weight(0.5f)
                .aspectRatio(1f),
            text = "여자",
            containerColor = if(selectedGender == "여자") LIGHT300 else WHITE,
            borderColor = if(selectedGender == "여자") LIGHT100 else GRAY200,
            fontWeight = if(selectedGender == "여자") FontWeight.SemiBold else FontWeight.Normal,
            onSelect = {
                onSelect("여자")
            }
        )
    }
}

@Composable
private fun GenderCard(
    modifier: Modifier,
    text: String,
    fontWeight: FontWeight = FontWeight.Normal,
    containerColor: Color,
    borderColor: Color,
    onSelect: () -> Unit,
){
    Card(
        modifier = modifier
            .noRippleClickable {
                onSelect()
            },
        shape = RoundedCornerShape(LARGE_CORNER_SIZE),
        colors = CardDefaults.cardColors(
            containerColor = containerColor
        ),
        border = BorderStroke(1.dp, borderColor),
    ){
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center,
        ){
            Text(
                text = text,
                fontSize = T3,
                color = BLACK,
                fontWeight = fontWeight
            )
        }
    }
}