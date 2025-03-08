package com.party.presentation.screen.join.gender.component

import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.party.common.WidthSpacer
import com.party.common.noRippleClickable
import com.party.common.ui.theme.BLACK
import com.party.common.ui.theme.GRAY200
import com.party.common.ui.theme.LARGE_CORNER_SIZE
import com.party.common.ui.theme.LIGHT100
import com.party.common.ui.theme.LIGHT300
import com.party.common.ui.theme.T3
import com.party.common.ui.theme.WHITE
import com.party.common.R

@Composable
fun SelectGenderArea(
    context: Context,
    selectedGender: String,
    onSelect: (String) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        GenderComponent(
            modifier = Modifier
                .weight(0.5f)
                .aspectRatio(1f),
            text = stringResource(id = R.string.join_gender3),
            containerColor = if(selectedGender == stringResource(id = R.string.join_gender3)) LIGHT300 else WHITE,
            borderColor = if(selectedGender == stringResource(id = R.string.join_gender3)) LIGHT100 else GRAY200,
            fontWeight = if(selectedGender == stringResource(id = R.string.join_gender3)) FontWeight.SemiBold else FontWeight.Normal,
            onSelect = {
                onSelect(context.getString(R.string.join_gender3))
            }
        )

        WidthSpacer(widthDp = 8.dp)

        GenderComponent(
            modifier = Modifier
                .weight(0.5f)
                .aspectRatio(1f),
            text = stringResource(id = R.string.join_gender4),
            containerColor = if(selectedGender == stringResource(id = R.string.join_gender4)) LIGHT300 else WHITE,
            borderColor = if(selectedGender == stringResource(id = R.string.join_gender4)) LIGHT100 else GRAY200,
            fontWeight = if(selectedGender == stringResource(id = R.string.join_gender4)) FontWeight.SemiBold else FontWeight.Normal,
            onSelect = {
                onSelect(context.getString(R.string.join_gender4))
            }
        )
    }
}

@Composable
private fun GenderComponent(
    modifier: Modifier,
    text: String,
    fontWeight: FontWeight = FontWeight.Normal,
    containerColor: Color,
    borderColor: Color,
    onSelect: () -> Unit,
) {
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

@Preview
@Composable
fun SelectGenderAreaPreview() {
    SelectGenderArea(
        context = LocalContext.current,
        selectedGender = "남자",
        onSelect = {}
    )
}

@Preview
@Composable
fun GenderComponentPreview() {
    GenderComponent(
        modifier = Modifier.size(163.dp),
        text = "남자",
        containerColor = WHITE,
        borderColor = GRAY200,
        onSelect = {}
    )
}

@Preview
@Composable
fun SelectGenderComponentPreview(){
    GenderComponent(
        modifier = Modifier.size(163.dp),
        text = "여자",
        fontWeight = FontWeight.SemiBold,
        containerColor = LIGHT300,
        borderColor = LIGHT100,
        onSelect = {}
    )
}