package com.party.presentation.screen.detail.detail_carrier

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.party.common.DetailCarrierData.mainSelectedCarrier
import com.party.common.DetailCarrierData.mainSelectedDetailPositionId
import com.party.common.LoadingProgressBar
import com.party.common.R
import com.party.common.ScreenExplainArea
import com.party.common.ServerApiResponse.ErrorResponse
import com.party.common.UIState
import com.party.common.snackBarMessage
import com.party.common.ui.theme.BLACK
import com.party.common.ui.theme.GRAY100
import com.party.common.ui.theme.GRAY400
import com.party.common.ui.theme.MEDIUM_PADDING_SIZE
import com.party.common.ui.theme.PRIMARY
import com.party.common.ui.theme.WHITE
import com.party.domain.model.user.detail.SaveCarrierList
import com.party.domain.model.user.detail.SaveCarrierRequest
import com.party.navigation.Screens
import com.party.presentation.screen.detail.ProfileIndicatorArea
import com.party.presentation.screen.detail.detail_carrier.component.DetailCarrierBottomArea
import com.party.presentation.screen.detail.detail_carrier.component.DetailCarrierScaffoldArea
import com.party.presentation.screen.detail.detail_carrier.component.PositionArea
import com.party.presentation.screen.detail.detail_carrier.component.isValidNextButton
import com.party.presentation.screen.detail.detail_carrier.viewmodel.DetailCarrierViewModel
import com.skydoves.sandwich.StatusCode
import kotlinx.coroutines.flow.collectLatest

@Composable
fun DetailCarrierScreen(
    context: Context,
    snackBarHostState: SnackbarHostState,
    navController: NavController,
    detailCarrierViewModel: DetailCarrierViewModel = hiltViewModel()
) {
    val saveCarrierState by detailCarrierViewModel.saveCarrierState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        detailCarrierViewModel.saveSuccessState.collectLatest {
            navController.navigate(Screens.SelectTendency1)
        }
    }
    when(saveCarrierState){
        is UIState.Idle -> {}
        is UIState.Loading -> { LoadingProgressBar() }
        is UIState.Success -> {}
        is UIState.Error -> {
            val errorResult = saveCarrierState.data as ErrorResponse
            when(errorResult.statusCode){
                StatusCode.Conflict.code -> {
                    snackBarMessage(message = errorResult.message!!, snackBarHostState = snackBarHostState)
                }
            }
        }
        is UIState.Exception -> {

            snackBarMessage(message = stringResource(id = R.string.common6), snackBarHostState = snackBarHostState)
        }
    }

    DetailCarrierScreenContent(
        context = context,
        onSkip = { navController.navigate(Screens.SelectTendency1) },
        onNextButtonClick = {
            if(isValidNextButton()) {
                val career = SaveCarrierList(
                    listOf(
                        SaveCarrierRequest(
                            positionId = mainSelectedDetailPositionId,
                            years = detailCarrierViewModel.convertToIntFromYear(mainSelectedCarrier),
                            careerType = "primary"
                        )
                    )
                )
                detailCarrierViewModel.saveCarrier(career = career)

            }
        },
        onGoToChoiceCarrierPosition = { navController.navigate(Screens.ChoiceCarrierPosition(isMain = it)) }
    )
}

@Composable
fun DetailCarrierScreenContent(
    context: Context,
    onSkip: () -> Unit,
    onNextButtonClick: () -> Unit,
    onGoToChoiceCarrierPosition: (Boolean) -> Unit,
){
    Scaffold(
        topBar = {
            DetailCarrierScaffoldArea(
                onNavigationClick = {}
            )
        }
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(WHITE)
                .padding(it)
                .padding(horizontal = MEDIUM_PADDING_SIZE)
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                ProfileIndicatorArea(
                    container1 = PRIMARY,
                    container2 = PRIMARY,
                    container3 = GRAY100,
                    textColor1 = BLACK,
                    textColor2 = BLACK,
                    textColor3 = GRAY400,
                    indicatorText = stringResource(id = R.string.detail_profile3),
                )

                ScreenExplainArea(
                    mainExplain = stringResource(id = R.string.detail_carrier1),
                    subExplain = stringResource(id = R.string.detail_carrier2),
                )

                // 주포지션, 부포지션 선택
                PositionArea(
                    context = context,
                    onGoToChoiceCarrierPosition = onGoToChoiceCarrierPosition
                )
            }

            // 하단 다음 버튼, 건너뛰기
            DetailCarrierBottomArea(
                onSkip = onSkip,
                onNextButtonClick = onNextButtonClick
            )
        }
    }

}

@Preview(showBackground = true)
@Composable
fun DetailCarrierScreenContentPreview() {
    DetailCarrierScreenContent(
        context = LocalContext.current,
        onSkip = {},
        onNextButtonClick = {},
        onGoToChoiceCarrierPosition = {}
    )
}