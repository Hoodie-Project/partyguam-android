package com.party.presentation.screen.party_apply

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.party.common.HeightSpacer
import com.party.common.LoadingProgressBar
import com.party.common.R
import com.party.common.UIState
import com.party.common.component.button.CustomButton
import com.party.common.snackBarMessage
import com.party.common.ui.theme.BLACK
import com.party.common.ui.theme.GRAY400
import com.party.common.ui.theme.LIGHT200
import com.party.common.ui.theme.LIGHT400
import com.party.common.ui.theme.MEDIUM_PADDING_SIZE
import com.party.common.ui.theme.PRIMARY
import com.party.common.ui.theme.WHITE
import com.party.domain.model.party.PartyApplyRequest
import com.party.presentation.screen.party_apply.component.PartyApplyInputReasonArea
import com.party.presentation.screen.party_apply.component.PartyApplyScaffoldArea
import com.party.presentation.screen.party_apply.component.PartyApplyTitleArea
import com.party.presentation.screen.party_apply.viewmodel.PartyApplyViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun PartyApplyScreen(
    navController: NavController,
    snackBarHostState: SnackbarHostState,
    partyApplyViewModel: PartyApplyViewModel,
    partyId: Int,
    partyRecruitmentId: Int,
) {
    // 입력된 지원 사유
    var inputApplyReason by remember { mutableStateOf("") }

    // 뒤로 가기 시 나오는 다이얼로그 여부
    var isShowBackDialog by remember { mutableStateOf(false) }

    val partyApplyState by partyApplyViewModel.partyRecruitmentApplyState.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = Unit) {
        partyApplyViewModel.applySuccess.collectLatest {
            navController.popBackStack()
        }
    }

    when(partyApplyState){
        is UIState.Idle -> {}
        is UIState.Loading -> { LoadingProgressBar()}
        is UIState.Success -> {}
        is UIState.Error -> {}
        is UIState.Exception -> { snackBarMessage(message = stringResource(id = R.string.common6), snackBarHostState = snackBarHostState) }
    }

    PartyApplyScreenContent(
        snackBarHostState = snackBarHostState,
        inputText = inputApplyReason,
        onValueChange = { inputApplyReason = it },
        onAllDeleteInputText = { inputApplyReason = "" },
        isShowBackDialog = isShowBackDialog,
        onVisibleDialog = { isShowBackDialog = it },
        onApply = {
            partyApplyViewModel.applyPartyRecruitment(
                partyId = partyId,
                partyRecruitmentId = partyRecruitmentId,
                partyApplyRequest = PartyApplyRequest(
                    message = inputApplyReason
                )
            )
        }
    )
}

@Composable
fun PartyApplyScreenContent(
    snackBarHostState: SnackbarHostState,
    inputText: String,
    onValueChange: (String) -> Unit,
    onAllDeleteInputText: () -> Unit,
    isShowBackDialog: Boolean,
    onVisibleDialog: (Boolean) -> Unit,
    onApply: () -> Unit,
) {
    Scaffold(
        snackbarHost = {
            SnackbarHost(
                hostState = snackBarHostState,
            )
        },
        topBar = {
            PartyApplyScaffoldArea(
                isShowDialog = isShowBackDialog,
                onNavigationClick = {
                    onVisibleDialog(true)
                },
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
                HeightSpacer(heightDp = 40.dp)
                PartyApplyTitleArea()
                HeightSpacer(heightDp = 20.dp)
                PartyApplyInputReasonArea(
                    inputText = inputText,
                    placeHolder = "지원 사유를 작성해 주세요.",
                    onValueChange = onValueChange,
                    onAllDeleteInputText = onAllDeleteInputText
                )
            }

            CustomButton(
                containerColor = if (inputText.isNotEmpty()) PRIMARY else LIGHT400,
                contentColor = if (inputText.isNotEmpty()) BLACK else GRAY400,
                borderColor = if (inputText.isNotEmpty()) PRIMARY else LIGHT200,
                onClick = { onApply() },
            )
            HeightSpacer(heightDp = 12.dp)
        }
    }
}

@Preview
@Composable
fun PartyApplyScreenContentPreview() {
    PartyApplyScreenContent(
        snackBarHostState = SnackbarHostState(),
        inputText = "지원합니다",
        onValueChange = {},
        onAllDeleteInputText = {},
        isShowBackDialog = false,
        onVisibleDialog = {},
        onApply = {},
    )
}