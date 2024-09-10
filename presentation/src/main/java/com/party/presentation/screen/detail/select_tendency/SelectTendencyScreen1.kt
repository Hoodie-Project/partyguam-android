package com.party.presentation.screen.detail.select_tendency

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircleOutline
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.party.common.HeightSpacer
import com.party.common.R
import com.party.common.TextComponent
import com.party.common.WidthSpacer
import com.party.common.noRippleClickable
import com.party.common.snackBarMessage
import com.party.common.ui.theme.BLACK
import com.party.common.ui.theme.DARK100
import com.party.common.ui.theme.EXTRA_LARGE_BUTTON_HEIGHT2
import com.party.common.ui.theme.GRAY100
import com.party.common.ui.theme.GRAY200
import com.party.common.ui.theme.LARGE_CORNER_SIZE
import com.party.common.ui.theme.LIGHT100
import com.party.common.ui.theme.LIGHT300
import com.party.common.ui.theme.LIGHT400
import com.party.common.ui.theme.PRIMARY
import com.party.common.ui.theme.T2
import com.party.common.ui.theme.T3
import com.party.common.ui.theme.WHITE
import com.party.navigation.Screens
import com.party.presentation.screen.detail.detail_profile.ProfileIndicatorArea

@Composable
fun SelectTendencyScreen1(
    navController: NavController,
    snackBarHostState: SnackbarHostState,
) {
    val selectedTendencyList by remember {
        mutableStateOf(mutableStateListOf<String>())
    }

    val isValid by remember {
        mutableStateOf(false)
    }.apply { value = selectedTendencyList.size == 2 }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
        ) {
            ProfileIndicatorArea(
                container1 = PRIMARY,
                container2 = PRIMARY,
                container3 = PRIMARY,
                textColor1 = BLACK,
                textColor2 = BLACK,
                textColor3 = BLACK,
            )

            HeightSpacer(heightDp = 32.dp)

            TextComponent(
                text = stringResource(id = R.string.select_tendency1),
                fontWeight = FontWeight.Bold,
                fontSize = T2,
            )

            HeightSpacer(heightDp = 12.dp)

            TextComponent(
                text = stringResource(id = R.string.select_tendency2),
                fontSize = T3,
            )

            HeightSpacer(heightDp = 40.dp)

            SelectTendencyArea1(
                selectedTendencyList = selectedTendencyList,
                onSelect = {
                    if(selectedTendencyList.contains(it)) {
                        selectedTendencyList.remove(it)
                    } else if(selectedTendencyList.size == 2){
                        snackBarMessage(snackBarHostState, "최대 2개까지 선택 가능합니다.")
                    }else {
                        selectedTendencyList.add(it)
                    }
                }
            )
        }

        TendencyBottomArea(
            navController = navController,
            routeScreens = Screens.SelectTendency,
            buttonText = stringResource(id = R.string.common1),
            textColor = BLACK,
            borderColor = if(isValid) LIGHT100 else GRAY200,
            containerColor = if(isValid) PRIMARY else WHITE,
        )
    }
}

@Composable
fun SelectTendencyArea1(
    selectedTendencyList: MutableList<String>,
    onSelect: (String) -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        itemsIndexed(
            items = timeList,
            key =  { index, _ ->
                index
            }
        ){_, item ->
            SelectTendencyAreaComponent(
                containerColor = if(selectedTendencyList.contains(item)) PRIMARY else WHITE,
                text = item,
                fontWeight = if(selectedTendencyList.contains(item)) FontWeight.Bold else FontWeight.Normal,
                iconColor = if(selectedTendencyList.contains(item)) DARK100 else GRAY200,
                onSelect = {
                    onSelect(it)
                },
            )
        }
    }
}

@Composable
fun SelectTendencyAreaComponent(
    containerColor: Color,
    text: String,
    fontWeight: FontWeight,
    iconColor: Color,
    onSelect: (String) -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(EXTRA_LARGE_BUTTON_HEIGHT2)
            .noRippleClickable {
                onSelect(text)
            },
        shape = RoundedCornerShape(LARGE_CORNER_SIZE),
        border = BorderStroke(1.dp, GRAY200),
        elevation = CardDefaults.cardElevation(2.dp),
        colors = CardDefaults.cardColors(
            containerColor = containerColor
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ){
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 20.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    imageVector = Icons.Default.CheckCircleOutline,
                    contentDescription = "check",
                    tint = iconColor,
                    modifier = Modifier
                        .size(20.dp)
                )
                WidthSpacer(widthDp = 6.dp)
                Text(
                    text = text,
                    fontWeight = fontWeight,
                    fontSize = T3
                )
            }
        }
    }
}

@Preview
@Composable
fun SelectTendencyAreaComponentPreview() {
    SelectTendencyAreaComponent(
        containerColor = GRAY100,
        text = "오전 (6시 - 12시)",
        fontWeight = FontWeight.Normal,
        iconColor = GRAY200,
        onSelect = {}
    )
}