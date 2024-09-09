package com.party.presentation.screen.detail_profile

import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.party.common.HeightSpacer
import com.party.common.R
import com.party.common.TextComponent
import com.party.common.WidthSpacer
import com.party.common.noRippleClickable
import com.party.common.snackBarMessage
import com.party.common.ui.theme.B2
import com.party.common.ui.theme.B3
import com.party.common.ui.theme.BLACK
import com.party.common.ui.theme.DARK200
import com.party.common.ui.theme.EXTRA_LARGE_BUTTON_HEIGHT2
import com.party.common.ui.theme.GRAY100
import com.party.common.ui.theme.GRAY200
import com.party.common.ui.theme.GRAY400
import com.party.common.ui.theme.GRAY500
import com.party.common.ui.theme.GRAY600
import com.party.common.ui.theme.LARGE_BUTTON_HEIGHT
import com.party.common.ui.theme.LARGE_CORNER_SIZE
import com.party.common.ui.theme.LIGHT400
import com.party.common.ui.theme.PRIMARY
import com.party.common.ui.theme.T3
import com.party.common.ui.theme.WHITE

@Composable
fun ProfileIndicatorArea(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        IndicatorComponent(
            number = "1",
            title = stringResource(id = R.string.detail_profile1),
            container = PRIMARY,
            textColor = BLACK
        )
        HorizontalDivider(
            modifier = Modifier
                .width(22.dp)
                .height(2.dp),
            color = GRAY100
        )
        IndicatorComponent(
            number = "2",
            title = stringResource(id = R.string.detail_profile2),
            container = GRAY100,
            textColor = GRAY400
        )
        HorizontalDivider(
            modifier = Modifier
                .width(22.dp)
                .height(2.dp),
            color = GRAY100
        )
        IndicatorComponent(
            number = "3",
            title = stringResource(id = R.string.detail_profile3),
            container = GRAY100,
            textColor = GRAY400
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
        IndicatorCircle(text = number, container = container)
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

@Composable
fun SelectLocationArea(
    selectedCity: String,
    selectedCountryList: MutableList<String>,
    onSelectCity: (String) -> Unit,
    onSelectCountry: (String) -> Unit,
    onDeleteCountry: (String) -> Unit,
    snackBarHostState: SnackbarHostState,
    context: Context,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        SelectCityArea(
            modifier = Modifier.weight(1f),
            selectedCity = selectedCity,
            onSelectCity = onSelectCity
        )

        WidthSpacer(widthDp = 12.dp)

        SelectCountryArea(
            modifier = Modifier.weight(2f),
            selectedCityName = selectedCity,
            selectedCountryList = selectedCountryList,
            onSelectCountry = onSelectCountry,
            onDeleteCountry = onDeleteCountry,
            snackBarHostState = snackBarHostState,
            context = context
        )
    }
}

@Composable
fun SelectCityArea(
    modifier: Modifier,
    selectedCity: String,
    onSelectCity: (String) -> Unit,
) {
    LazyColumn(
        modifier = modifier
            .border(
                border = BorderStroke(1.dp, GRAY200),
                shape = RoundedCornerShape(LARGE_CORNER_SIZE)
            )
            .height(320.dp)
    ) {
        itemsIndexed(
            items = locationList,
            key = { index, _ ->
                index
            }
        ){ _, item ->
            CityComponent(
                containerColor = if(selectedCity == item) LIGHT400 else WHITE,
                text = item,
                textColor = if(selectedCity == item) DARK200 else GRAY400,
                onSelectCity = { onSelectCity(it) }
            )
        }
    }
}

@Composable
fun SelectCountryArea(
    modifier: Modifier,
    selectedCityName: String,
    selectedCountryList: MutableList<String>,
    onSelectCountry: (String) -> Unit,
    onDeleteCountry: (String) -> Unit,
    snackBarHostState: SnackbarHostState,
    context: Context,
) {
    LazyVerticalGrid(
        modifier = modifier
            .border(BorderStroke(1.dp, GRAY200), shape = RoundedCornerShape(LARGE_CORNER_SIZE))
            .height(320.dp),
        columns = GridCells.Fixed(2),
    ){
        itemsIndexed(
            items = test(selectedCityName),
            key = { index, _ ->
                index
            }
        ){ _, item ->
            CountryComponent(
                countryName = item,
                textColor = if(selectedCountryList.contains(item)) PRIMARY else GRAY600,
                border = if(selectedCountryList.contains(item)) BorderStroke(1.dp, PRIMARY) else null,
                selectedCityName = selectedCityName,
                onSelectCountry = {
                    onSelectCountry(it)
                },
                onDeleteCountry = {
                    onDeleteCountry(it)
                },
                snackBarHostState = snackBarHostState,
                context = context,
                selectedCountryList = selectedCountryList
            )
        }
    }
}

@Composable
fun CityComponent(
    containerColor: Color,
    text: String,
    textColor: Color,
    onSelectCity: (String) -> Unit,
) {
    Card(
        modifier = Modifier
            .height(EXTRA_LARGE_BUTTON_HEIGHT2)
            .noRippleClickable {
                onSelectCity(text)
            },
        colors = CardDefaults.cardColors(
            containerColor = containerColor
        ),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center,
        ){
            Text(
                text = text,
                color = textColor,
                fontSize = T3,
                fontWeight = FontWeight.Bold,
            )
        }
    }
}

@Composable
fun CountryComponent(
    context: Context,
    snackBarHostState: SnackbarHostState,
    selectedCityName: String,
    countryName: String,
    selectedCountryList: MutableList<String>,
    textColor: Color,
    border: BorderStroke? = null,
    onSelectCountry: (String) -> Unit,
    onDeleteCountry: (String) -> Unit,
) {
    Card(
        modifier = Modifier
            .height(EXTRA_LARGE_BUTTON_HEIGHT2)
            .noRippleClickable {
                test1(
                    context = context,
                    snackBarHostState = snackBarHostState,
                    selectedCityName = selectedCityName,
                    countryName = countryName,
                    selectedCountryList = selectedCountryList,
                    onSelectCountry = onSelectCountry,
                    onDeleteCountry = onDeleteCountry,
                )
            }
            .padding(1.dp),
        colors = CardDefaults.cardColors(
            containerColor = WHITE
        ),
        border = border,
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center,
        ){
            Text(
                text = countryName,
                color = textColor,
                fontSize = T3,
                fontWeight = FontWeight.Bold,
            )
        }
    }
}

@Composable
fun BottomArea(
    selectedCity: String,
    selectedCountryList: MutableList<String>,
    onDelete: (String) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        SelectedCityList(
            selectedCity = selectedCity,
            selectedCountryList = selectedCountryList,
            onDelete = onDelete
        )

        HeightSpacer(heightDp = 12.dp)

        DetailProfileNextButton(
            text = stringResource(id = R.string.common1),
            textColor = if(selectedCountryList.size == 3) BLACK else GRAY400,
            containerColor = if(selectedCountryList.size == 3) PRIMARY else LIGHT400,
        )

        HeightSpacer(heightDp = 12.dp)

        TextComponent(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp),
            text = stringResource(id = R.string.common3),
            fontSize = B2,
            textColor = GRAY500,
            textAlign = Alignment.Center,
            textDecoration = TextDecoration.Underline,
        )
    }
}

@Composable
fun SelectedCityList(
    selectedCity: String,
    selectedCountryList: MutableList<String>,
    onDelete: (String) -> Unit,
) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        itemsIndexed(
            items = selectedCountryList,
            key = { index, _ ->
                index
            }
        ) { _, item ->
            SelectCityComponent(
                selectedCityName = selectedCity,
                selectedCity2Name = item,
                onDelete = { onDelete(it) }
            )
            WidthSpacer(widthDp = 8.dp)
        }
    }
}

@Composable
fun SelectCityComponent(
    selectedCityName: String,
    selectedCity2Name: String,
    onDelete: (String) -> Unit,
) {
    Card(
        modifier = Modifier
            .wrapContentWidth()
            .height(36.dp),
        colors = CardDefaults.cardColors(
            containerColor = WHITE
        ),
        border = BorderStroke(1.dp, PRIMARY),
        shape = RoundedCornerShape(LARGE_CORNER_SIZE),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center,
        ){
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    modifier = Modifier.padding(start = 12.dp),
                    text = "$selectedCityName $selectedCity2Name",
                    color = BLACK,
                    fontSize = B2,
                )
                IconButton(
                    onClick = {
                        onDelete(selectedCity2Name)
                    },
                    interactionSource = MutableInteractionSource(),
                ) {
                    Icon(
                        modifier = Modifier.size(12.dp),
                        imageVector = Icons.Default.Close,
                        contentDescription = "close",
                    )
                }
            }
        }
    }
}

@Composable
fun DetailProfileNextButton(
    text: String,
    textColor: Color,
    containerColor: Color,
) {
    Button(
        onClick = {

        },
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

fun test(selectedCityName: String,): List<String>{
    return when(selectedCityName){
        "서울" -> seoulList.second
        "경기" -> gyeonggiList.second
        else -> {seoulList.second}
    }
}

fun test1(
    context: Context,
    snackBarHostState: SnackbarHostState,
    selectedCityName: String,
    countryName: String,
    selectedCountryList: MutableList<String>,
    onSelectCountry: (String) -> Unit,
    onDeleteCountry: (String) -> Unit,
){
    if (selectedCityName.isEmpty())
        snackBarMessage(snackBarHostState, context.getString(R.string.snackbar1))
    else
        if(selectedCountryList.contains(countryName)) onDeleteCountry(countryName)
        else if(selectedCountryList.size == 3) snackBarMessage(snackBarHostState, context.getString(R.string.snackbar2))
        else onSelectCountry(countryName)

}

@Preview
@Composable
fun SelectCityComponentPreview() {
    SelectCityComponent(
        selectedCityName = "서울",
        selectedCity2Name = "강남구",
        onDelete = {}
    )
}