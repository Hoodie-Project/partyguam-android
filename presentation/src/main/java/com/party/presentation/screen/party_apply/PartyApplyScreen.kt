package com.party.presentation.screen.party_apply

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.party.common.utils.HeightSpacer
import com.party.common.utils.LoadingProgressBar
import com.party.common.component.dialog.TwoButtonDialog
import com.party.common.component.snackbar.CustomSnackBar
import com.party.guam.design.BLACK
import com.party.guam.design.MEDIUM_PADDING_SIZE
import com.party.guam.design.WHITE
import com.party.presentation.screen.party_apply.component.PartyApplyButton
import com.party.presentation.screen.party_apply.component.PartyApplyInputReasonArea
import com.party.presentation.screen.party_apply.component.PartyApplyScaffoldArea
import com.party.presentation.screen.party_apply.component.PartyApplyTitleArea
import com.party.presentation.screen.party_apply.viewmodel.PartyApplyViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun PartyApplyRoute(
    navController: NavController,
    snackBarHostState: SnackbarHostState,
    partyId: Int,
    partyRecruitmentId: Int,
    partyApplyViewModel: PartyApplyViewModel = hiltViewModel(),
) {
    val applyState by partyApplyViewModel.applyState.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = Unit) {
        partyApplyViewModel.successState.collectLatest {
            navController.popBackStack()
        }
    }

    LaunchedEffect(key1 = Unit) {
        partyApplyViewModel.backState.collectLatest {
            navController.popBackStack()
        }
    }

    PartyApplyScreen(
        snackBarHostState = snackBarHostState,
        applyState = applyState,
        partyId = partyId,
        partyRecruitmentId = partyRecruitmentId,
        onAction = { action ->
            when(action){
                is PartyApplyAction.OnShowDialog -> { partyApplyViewModel.onAction(action) }
                is PartyApplyAction.OnDismissBackDialog -> { partyApplyViewModel.onAction(action) }
                is PartyApplyAction.OnChangeInputText -> { partyApplyViewModel.onAction(action) }
                is PartyApplyAction.OnAllDeleteInputText -> { partyApplyViewModel.onAction(action) }
                is PartyApplyAction.OnApply -> { partyApplyViewModel.onAction(action) }
            }
        },
    )

    if(applyState.isShowBackDialog){
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(BLACK.copy(alpha = 0.2f))
        ) {
            TwoButtonDialog(
                dialogTitle = "나가기",
                description = "입력한 내용들이 모두 초기화됩니다.\n나가시겠습니까?",
                cancelButtonText = "취소",
                confirmButtonText = "나가기",
                onCancel = { partyApplyViewModel.dismissBackDialog() },
                onConfirm = { partyApplyViewModel.navigationBack() },
            )
        }
    }
}

@Composable
fun PartyApplyScreen(
    snackBarHostState: SnackbarHostState,
    applyState: PartyApplyState,
    partyId: Int,
    partyRecruitmentId: Int,
    onAction: (PartyApplyAction) -> Unit,
) {
    val focusManager = LocalFocusManager.current

    Scaffold(
        modifier = Modifier
            .blur(
                radiusX = if (applyState.isShowBackDialog) 10.dp else 0.dp,
                radiusY = if (applyState.isShowBackDialog) 10.dp else 0.dp,
            ),
        snackbarHost = {
            SnackbarHost(
                hostState = snackBarHostState,
                snackbar = { data ->
                    CustomSnackBar(
                        message = data.visuals.message
                    )
                }
            )
        },
        topBar = {
            PartyApplyScaffoldArea(
                onNavigationClick = {
                    onAction(PartyApplyAction.OnShowDialog)
                },
            )
        }
    ) {
        if(applyState.isLoading){
            LoadingProgressBar()
        }else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(WHITE)
                    .padding(it)
                    .padding(horizontal = MEDIUM_PADDING_SIZE)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {
                        focusManager.clearFocus()  // 필드 외부 터치 시 포커스 해제 → 키보드 내려감
                    }
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                ) {
                    HeightSpacer(heightDp = 40.dp)
                    PartyApplyTitleArea()
                    HeightSpacer(heightDp = 20.dp)
                    PartyApplyInputReasonArea(
                        inputText = applyState.inputApplyReason,
                        placeHolder = "지원 사유를 작성해 주세요.",
                        onValueChange = { inputText -> onAction(PartyApplyAction.OnChangeInputText(inputText)) },
                        onAllDeleteInputText = { onAction(PartyApplyAction.OnAllDeleteInputText) }
                    )
                }

                PartyApplyButton(
                    inputApplyReason = applyState.inputApplyReason,
                    onClick = { onAction(PartyApplyAction.OnApply(partyId = partyId, partyRecruitmentId = partyRecruitmentId)) }
                )
                HeightSpacer(heightDp = 12.dp)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PartyApplyScreenContentPreview() {
    PartyApplyScreen(
        snackBarHostState = SnackbarHostState(),
        applyState = PartyApplyState(),
        partyId = 1,
        partyRecruitmentId = 1,
        onAction = {},
    )
}