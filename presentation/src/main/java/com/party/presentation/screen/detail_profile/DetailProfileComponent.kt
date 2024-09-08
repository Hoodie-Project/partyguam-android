package com.party.presentation.screen.detail_profile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
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
import com.party.common.ui.theme.GREEN
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
    selectedCityName: String,
    selectedCity2NameList: MutableList<String>,
    onSelectCity: (String) -> Unit,
    onSelectCity2: (String) -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        SelectCityArea(
            modifier = Modifier.weight(1f),
            selectedCityName = selectedCityName,
            onSelectCity = onSelectCity
        )

        WidthSpacer(widthDp = 12.dp)

        SelectCity2Area(
            modifier = Modifier.weight(2f),
            selectedCityName = selectedCityName,
            selectedCity2NameList = selectedCity2NameList,
            onSelectCity2 = onSelectCity2
        )
    }
}

@Composable
fun SelectCityArea(
    modifier: Modifier,
    selectedCityName: String,
    onSelectCity: (String) -> Unit,
) {
    LazyColumn(
        modifier = modifier
            .border(BorderStroke(1.dp, GRAY200), shape = RoundedCornerShape(LARGE_CORNER_SIZE))
    ) {
        itemsIndexed(
            items = locationList,
            key = { index, item ->
                index
            }
        ){ _, item ->
            CityComponent(
                containerColor = if(selectedCityName == item) LIGHT400 else WHITE,
                text = item,
                textColor = if(selectedCityName == item) DARK200 else GRAY400,
                onSelectCity = { onSelectCity(it) }
            )
        }
    }
}

@Composable
fun SelectCity2Area(
    modifier: Modifier,
    selectedCityName: String,
    selectedCity2NameList: MutableList<String>,
    onSelectCity2: (String) -> Unit,
) {
    LazyVerticalGrid(
        modifier = modifier
            .border(BorderStroke(1.dp, GRAY200), shape = RoundedCornerShape(LARGE_CORNER_SIZE)),
        columns = GridCells.Fixed(2),
    ){
        itemsIndexed(
            items = test(selectedCityName),
            key = { index, _ ->
                index
            }
        ){ _, item ->
            CityComponent2(
                text = item,
                textColor = if(selectedCity2NameList.contains(item)) PRIMARY else GRAY600,
                border = if(selectedCity2NameList.contains(item)) BorderStroke(1.dp, PRIMARY) else null,
                onSelectCity2 = {
                    onSelectCity2(it)
                }
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
fun CityComponent2(
    text: String,
    textColor: Color,
    border: BorderStroke? = null,
    onSelectCity2: (String) -> Unit,
) {
    Card(
        modifier = Modifier
            .height(EXTRA_LARGE_BUTTON_HEIGHT2)
            .noRippleClickable {
                onSelectCity2(text)
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
                text = text,
                color = textColor,
                fontSize = T3,
                fontWeight = FontWeight.Bold,
            )
        }
    }
}

@Composable
fun BottomArea(
    selectedCityName: String,
    selectedCity2NameList: MutableList<String>,
    onDelete: (String) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(147.dp)
    ) {
        SelectedCityList(
            selectedCityName = selectedCityName,
            selectedCity2NameList = selectedCity2NameList,
            onDelete = onDelete
        )

        HeightSpacer(heightDp = 12.dp)

        DetailProfileNextButton(
            text = "다음",
            textColor = GRAY400,
            containerColor = LIGHT400,
        )

        HeightSpacer(heightDp = 24.dp)

        TextComponent(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            text = "건너뛰기",
            fontSize = B2,
            textColor = GRAY500,
            textAlign = Alignment.Center,
            textDecoration = TextDecoration.Underline,
        )
    }
}

@Composable
fun SelectedCityList(
    selectedCityName: String,
    selectedCity2NameList: MutableList<String>,
    onDelete: (String) -> Unit,
) {
    HeightSpacer(heightDp = 16.dp)

    LazyRow(
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        itemsIndexed(
            items = selectedCity2NameList,
            key = { index, _ ->
                index
            }
        ) { _, item ->
            SelectCityComponent(
                selectedCityName = selectedCityName,
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
            .width(103.dp)
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
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 12.dp)
            ) {
                Text(
                    text = "$selectedCityName $selectedCity2Name",
                    color = BLACK,
                    fontSize = B2,
                )
                IconButton(
                    onClick = {
                        onDelete(selectedCity2Name)
                    }
                ) {
                    Icon(
                        modifier = Modifier.size(6.dp),
                        painter = painterResource(id = R.drawable.close2),
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

@Preview
@Composable
fun SelectCityComponentPreview() {
    SelectCityComponent(
        selectedCityName = "서울",
        selectedCity2Name = "강남구",
        onDelete = {}
    )
}