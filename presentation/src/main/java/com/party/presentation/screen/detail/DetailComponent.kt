package com.party.presentation.screen.detail

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.party.common.R
import com.party.common.utils.TextComponent
import com.party.common.utils.WidthSpacer
import com.party.common.component.icon.DrawableIcon
import com.party.common.utils.snackBarMessage
import com.party.guam.design.B2
import com.party.guam.design.B3
import com.party.guam.design.BLACK
import com.party.guam.design.LARGE_BUTTON_HEIGHT
import com.party.guam.design.LARGE_CORNER_SIZE
import com.party.guam.design.PRIMARY
import com.party.domain.model.user.detail.Location
import com.party.presentation.screen.detail.detail_profile.SELECTED_LOCATION_COUNT

@Composable
fun DetailProfileNextButton(
    onClick: () -> Unit = {},
    text: String,
    textColor: Color,
    containerColor: Color,
) {
    Button(
        onClick = { onClick() },
        modifier = Modifier
            .fillMaxWidth()
            .height(LARGE_BUTTON_HEIGHT),
        shape = RoundedCornerShape(LARGE_CORNER_SIZE),
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor
        ),
    ){
        Text(
            text = text,
            color = textColor,
            fontSize = B2,
            fontWeight = FontWeight.Bold,
        )
    }
}

@Composable
fun ProfileIndicatorArea(
    container1: Color,
    container2: Color,
    container3: Color,
    textColor1: Color,
    textColor2: Color,
    textColor3: Color,
    indicatorText: String,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        IndicatorComponent(
            number = "1",
            title = stringResource(id = R.string.detail_profile1),
            container = container1,
            textColor = textColor1
        )
        HorizontalDivider(
            modifier = Modifier
                .width(22.dp)
                .height(2.dp),
            color = PRIMARY,
        )
        IndicatorComponent(
            number = "2",
            title = stringResource(id = R.string.detail_profile2),
            container = container2,
            textColor = textColor2
        )
        HorizontalDivider(
            modifier = Modifier
                .width(22.dp)
                .height(2.dp),
            color = PRIMARY
        )
        IndicatorComponent(
            number = "3",
            title = indicatorText,
            container = container3,
            textColor = textColor3
        )
    }
}

@Composable
fun IndicatorComponent(
    number: String,
    title: String,
    container: Color,
    textColor: Color,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if(number != "3"){
            DrawableIcon(
                icon = painterResource(R.drawable.icon_check_primary),
                contentDescription = "",
                modifier = Modifier.size(24.dp),
                tintColor = Color.Unspecified
            )
        }else {
            IndicatorCircle(
                text = number,
                container = container
            )
        }

        WidthSpacer(widthDp = 6.dp)
        TextComponent(text = title, fontSize = B3, fontWeight = FontWeight.Bold, textColor = textColor)
    }
}

@Composable
fun IndicatorCircle(
    text: String,
    container: Color,
) {
    Card(
        modifier = Modifier.size(24.dp),
        shape = CircleShape,
        colors = CardDefaults.cardColors(
            containerColor = container
        ),
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ){
            Text(
                text = text,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = BLACK,
            )
        }
    }
}

fun componentClick(
    context: Context,
    snackBarHostState: SnackbarHostState,
    selectedProvinceName: String,
    locationResponse: Location,
    selectedCountryList: MutableList<Pair<String, Int>>,
    onSelectCountry: (Pair<String, Int>) -> Unit,
    onDeleteCountry: (Pair<String, Int>) -> Unit,
){
    if (selectedProvinceName.isEmpty())
        snackBarMessage(snackBarHostState, context.getString(R.string.snackbar1))
    else
        if( isContainLocation(selectedCountryList, locationResponse.city) ) onDeleteCountry(Pair(locationResponse.city, locationResponse.id))
        else if(selectedCountryList.size == SELECTED_LOCATION_COUNT) snackBarMessage(snackBarHostState, context.getString(R.string.snackbar2))
        else onSelectCountry(Pair(locationResponse.city, locationResponse.id))
}

private fun isContainLocation(selectedLocationList: MutableList<Pair<String, Int>>, selectedLocation: String): Boolean{
    return selectedLocationList.any { it.first.contains(selectedLocation) }
}