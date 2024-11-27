package com.party.presentation.screen.party_apply

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.party.common.ApplyButtonArea
import com.party.common.HeightSpacer
import com.party.common.component.dialog.TwoButtonDialog
import com.party.common.noRippleClickable
import com.party.common.ui.theme.BLACK
import com.party.common.ui.theme.GRAY400
import com.party.common.ui.theme.LIGHT200
import com.party.common.ui.theme.LIGHT400
import com.party.common.ui.theme.MEDIUM_PADDING_SIZE
import com.party.common.ui.theme.PRIMARY
import com.party.common.ui.theme.WHITE
import com.party.presentation.screen.party_apply.component.PartyApplyInputReasonArea
import com.party.presentation.screen.party_apply.component.PartyApplyScaffoldArea
import com.party.presentation.screen.party_apply.component.PartyApplyTitleArea
import com.party.presentation.screen.party_apply.viewmodel.PartyApplyViewModel

@Composable
fun PartyApplyScreen(
    navController: NavController,
    snackBarHostState: SnackbarHostState,
    partyApplyViewModel: PartyApplyViewModel,
    partyId: Int,
    partyRecruitmentId: Int,
) {
    var inputText by remember { mutableStateOf("") }

    var isShowBackDialog by remember { mutableStateOf(false) }

    PartyApplyScreenContent(
        snackBarHostState = snackBarHostState,
        inputText = inputText,
        onValueChange = { inputText = it },
        onAllDeleteInputText = { inputText = "" },
        onNavigationClick = { navController.popBackStack() },
        isShowBackDialog = isShowBackDialog,
        onVisibleDialog = { isShowBackDialog = it }
    )
}

@Composable
fun PartyApplyScreenContent(
    snackBarHostState: SnackbarHostState,
    inputText: String,
    onValueChange: (String) -> Unit,
    onAllDeleteInputText: () -> Unit,
    onNavigationClick: () -> Unit,
    isShowBackDialog: Boolean,
    onVisibleDialog: (Boolean) -> Unit,
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
                .blur(
                    radiusX = if (isShowBackDialog) 10.dp else 0.dp,
                    radiusY = if (isShowBackDialog) 10.dp else 0.dp,
                )
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

            ApplyButtonArea(
                containerColor = if (inputText.isNotEmpty()) PRIMARY else LIGHT400,
                contentColor = if (inputText.isNotEmpty()) BLACK else GRAY400,
                borderColor = if (inputText.isNotEmpty()) PRIMARY else LIGHT200,
                onClick = { }
            )
            HeightSpacer(heightDp = 12.dp)
        }

        if (isShowBackDialog) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(BLACK.copy(alpha = 0.7f))
                    .noRippleClickable { onVisibleDialog(false) }
            ) {
                TwoButtonDialog(
                    dialogTitle = "나가기",
                    description = "입력한 내용들이 모두 초기화됩니다.\n나가시겠습니까?",
                    cancelButtonText = "취소",
                    confirmButtonText = "나가기",
                    onCancel = { onVisibleDialog(false) },
                    onConfirm = {
                        onVisibleDialog(false)
                        onNavigationClick()
                    }
                )
            }
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
        onNavigationClick = {},
        isShowBackDialog = false,
        onVisibleDialog = {}
    )
}