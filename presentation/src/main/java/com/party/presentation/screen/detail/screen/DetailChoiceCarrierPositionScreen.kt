package com.party.presentation.screen.detail.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.party.common.R
import com.party.common.component.bottomsheet.list.positionList
import com.party.common.utils.HeightSpacer
import com.party.guam.design.BLACK
import com.party.guam.design.GRAY400
import com.party.guam.design.LIGHT200
import com.party.guam.design.MEDIUM_PADDING_SIZE
import com.party.guam.design.PRIMARY
import com.party.guam.design.WHITE
import com.party.presentation.screen.detail.action.DetailProfileAction
import com.party.presentation.screen.detail.component.ChoiceCarrierPositionScaffoldArea
import com.party.presentation.screen.detail.component.DetailProfileNextButton
import com.party.presentation.screen.detail.component.careerList
import com.party.presentation.screen.detail.state.DetailProfileState
import com.party.presentation.screen.detail.viewmodel.DetailProfileViewModel
import com.party.presentation.screen.home_detail_profile.component.SelectCareerSection
import com.party.presentation.screen.home_detail_profile.component.SelectMainPositionSection
import com.party.presentation.screen.home_detail_profile.component.SelectedSubPositionSection

@Composable
fun DetailChoiceCarrierPositionRoute(
    viewModel: DetailProfileViewModel,
    navController: NavHostController,
    isMain: Boolean
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    DetailChoiceCarrierPositionScreen(
        isMain = isMain,
        state = state,
        onNavigationClick = {
            if(isMain){
                viewModel.onResetMainPosition()
            } else {
                viewModel.onResetSubPosition()
            }
            navController.popBackStack()
        },
        onAction = { action -> viewModel.onAction(action) },
        onAdd = { navController.popBackStack() },
    )
}

@Composable
private fun DetailChoiceCarrierPositionScreen(
    isMain: Boolean,
    state: DetailProfileState,
    onNavigationClick: () -> Unit,
    onAction: (DetailProfileAction) -> Unit = {},
    onAdd: () -> Unit,
) {
    Scaffold(
        topBar = {
            ChoiceCarrierPositionScaffoldArea(
                onNavigationClick = onNavigationClick
            )
        }
    ) {
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
                HeightSpacer(heightDp = 20.dp)
                SelectCareerSection(
                    title = stringResource(id = R.string.choice_carrier_position1),
                    list = careerList,
                    selectedCareer = if(isMain) state.firstCareer else state.secondCareer,
                    onSelectCareer = {
                        if (isMain) {
                            onAction(DetailProfileAction.OnClickFirstCareer(career = it))
                        } else {
                            onAction(DetailProfileAction.OnClickSecondCareer(career = it))
                        }
                    }
                )
                HeightSpacer(heightDp = 20.dp)
                SelectMainPositionSection(
                    title = stringResource(id = R.string.choice_carrier_position2),
                    list = positionList,
                    selectedPosition = if(isMain) state.firstMainPosition else state.secondMainPosition,
                    onSelectMainPosition = {
                        if (isMain) {
                            onAction(DetailProfileAction.OnClickFirstMainPosition(mainPosition = it))
                        } else {
                            onAction(DetailProfileAction.OnClickSecondMainPosition(mainPosition = it))
                        }
                    }
                )
                HeightSpacer(heightDp = 20.dp)
                SelectedSubPositionSection(
                    subPositionList = if(isMain) state.subPositionListFirst else state.subPositionListSecond,
                    selectedDetailPosition = if(isMain) state.firstSubPosition else state.secondSubPosition,
                    onClickSubPosition = {
                        if (isMain) {
                            onAction(DetailProfileAction.OnClickFirstSubPosition(positionList = it))
                        } else {
                            onAction(DetailProfileAction.OnClickSecondSubPosition(positionList = it))
                        }
                    }
                )
            }

            HeightSpacer(heightDp = 12.dp)

            val isCompleted = if (isMain) {
                state.firstCareer.isNotEmpty() && state.firstMainPosition.isNotEmpty() && state.firstSubPosition.isNotEmpty()
            } else {
                state.secondCareer.isNotEmpty() && state.secondMainPosition.isNotEmpty() && state.secondSubPosition.isNotEmpty()
            }

            DetailProfileNextButton(
                text = stringResource(id = R.string.choice_carrier_position3),
                textColor = if (isCompleted) BLACK else GRAY400,
                containerColor = if (isCompleted) PRIMARY else LIGHT200,
                onClick = onAdd
            )
        }
    }
}