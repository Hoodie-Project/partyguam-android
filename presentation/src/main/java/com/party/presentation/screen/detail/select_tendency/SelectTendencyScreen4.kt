package com.party.presentation.screen.detail.select_tendency

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.party.common.HeightSpacer
import com.party.common.R
import com.party.common.TextComponent
import com.party.common.snackBarMessage
import com.party.common.ui.theme.BLACK
import com.party.common.ui.theme.DARK100
import com.party.common.ui.theme.GRAY200
import com.party.common.ui.theme.LIGHT100
import com.party.common.ui.theme.PRIMARY
import com.party.common.ui.theme.T2
import com.party.common.ui.theme.T3
import com.party.common.ui.theme.WHITE
import com.party.navigation.Screens
import com.party.presentation.screen.detail.detail_profile.ProfileIndicatorArea

@Composable
fun SelectTendencyScreen4(
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
                indicatorText = stringResource(id = R.string.detail_profile6),
            )

            HeightSpacer(heightDp = 32.dp)

            TextComponent(
                text = stringResource(id = R.string.select_tendency7),
                fontWeight = FontWeight.Bold,
                fontSize = T2,
            )

            HeightSpacer(heightDp = 12.dp)

            TextComponent(
                text = stringResource(id = R.string.select_tendency6),
                fontSize = T3,
            )

            HeightSpacer(heightDp = 40.dp)

            SelectTendencyArea4(
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
            routeScreens = Screens.SelectTendencyComplete,
            buttonText = stringResource(id = R.string.common1),
            textColor = BLACK,
            borderColor = if(isValid) LIGHT100 else GRAY200,
            containerColor = if(isValid) PRIMARY else WHITE,
        )
    }
}

@Composable
fun SelectTendencyArea4(
    selectedTendencyList: MutableList<String>,
    onSelect: (String) -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        itemsIndexed(
            items = challengeList,
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