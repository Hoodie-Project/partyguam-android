package com.party.presentation.screen.detail.select_tendency

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.party.common.LoadingProgressBar
import com.party.common.R
import com.party.common.ScreenExplainArea
import com.party.common.ServerApiResponse.SuccessResponse
import com.party.common.UIState
import com.party.common.makeAccessToken
import com.party.common.snackBarMessage
import com.party.common.ui.theme.BLACK
import com.party.common.ui.theme.DARK100
import com.party.common.ui.theme.DARK400
import com.party.common.ui.theme.GRAY200
import com.party.common.ui.theme.GRAY400
import com.party.common.ui.theme.LIGHT200
import com.party.common.ui.theme.LIGHT300
import com.party.common.ui.theme.LIGHT400
import com.party.common.ui.theme.PRIMARY
import com.party.common.ui.theme.WHITE
import com.party.domain.model.user.detail.PersonalityListOptionResponse
import com.party.navigation.Screens
import com.party.presentation.screen.detail.ProfileIndicatorArea

@Composable
fun SelectTendencyScreen1(
    context: Context,
    navController: NavController,
    snackBarHostState: SnackbarHostState,
    selectTendencyViewModel: SelectTendencyViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        selectTendencyViewModel.getAccessToken().join()
    }

    val accessToken by selectTendencyViewModel.accessToken.collectAsState()

    if(accessToken.isNotEmpty()){
        LaunchedEffect(Unit) {
            selectTendencyViewModel.getPersonalityList(accessToken = makeAccessToken(context = context, token = accessToken))
        }
    }

    val personalityListState by selectTendencyViewModel.personalityState.collectAsState()
    val personalityListResult = personalityListState.data

    val selectedTendencyList by remember {
        mutableStateOf(mutableStateListOf<PersonalityListOptionResponse>())
    }

    val isValid by remember {
        mutableStateOf(false)
    }.apply { value = selectedTendencyList.size in 1..2 }

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
                indicatorText = stringResource(id = R.string.detail_profile3),
            )

            ScreenExplainArea(
                mainExplain = stringResource(id = R.string.select_tendency1),
                subExplain = stringResource(id = R.string.select_tendency2),
            )

            when(personalityListState){
                is UIState.Idle -> {}
                is UIState.Loading -> { LoadingProgressBar() }
                is UIState.Success -> {
                    val successResult = personalityListResult as SuccessResponse
                    val successResult2 = successResult.data?.filter { it.id == 1 }
                    SelectTendencyArea1(
                        selectedTendencyList = selectedTendencyList,
                        getPersonalityList = successResult2?.get(0)?.personalityOption ?: emptyList(),
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
                is UIState.Error -> {}
                is UIState.Exception -> {}
            }
        }

        TendencyBottomArea(
            navController = navController,
            routeScreens = Screens.SelectTendency2,
            buttonText = stringResource(id = R.string.common1),
            textColor = if(isValid) BLACK else GRAY400,
            borderColor = if(isValid) PRIMARY else LIGHT200,
            containerColor = if(isValid) PRIMARY else LIGHT400,
            onClick = {
                if(isValid){
                    navController.navigate(Screens.SelectTendency2)
                }
            }
        )
    }
}

@Composable
fun SelectTendencyArea1(
    selectedTendencyList: MutableList<PersonalityListOptionResponse>,
    getPersonalityList: List<PersonalityListOptionResponse>,
    onSelect: (PersonalityListOptionResponse) -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        itemsIndexed(
            items = getPersonalityList,
            key =  { index, _ ->
                index
            }
        ){_, item ->
            SelectTendencyAreaComponent(
                containerColor = if(selectedTendencyList.contains(item)) LIGHT300 else WHITE,
                item = item,
                fontWeight = if(selectedTendencyList.contains(item)) FontWeight.SemiBold else FontWeight.Normal,
                iconColor = if(selectedTendencyList.contains(item)) PRIMARY else GRAY200,
                onSelect = { onSelect(it) },
                textColor = if(selectedTendencyList.contains(item)) DARK400 else BLACK,
            )
        }
    }
}