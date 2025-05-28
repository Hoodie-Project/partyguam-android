package com.party.presentation.screen.reports

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.party.common.utils.HeightSpacer
import com.party.common.utils.snackBarMessage
import com.party.guam.design.MEDIUM_PADDING_SIZE
import com.party.guam.design.WHITE
import com.party.presentation.screen.reports.component.ReportsButton
import com.party.presentation.screen.reports.component.ReportsInputReasonArea
import com.party.presentation.screen.reports.component.ReportsScaffoldArea
import com.party.presentation.screen.reports.component.ReportsTitleArea
import kotlinx.coroutines.flow.collectLatest

@Composable
fun ReportsScreenRoute(
    navController: NavHostController,
    snackBarHostState: SnackbarHostState,
    typeId: Int,
    reportsViewModel: ReportsViewModel = hiltViewModel()
) {
    val state by reportsViewModel.reportsState.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = Unit) {
        reportsViewModel.successReports.collectLatest {
            snackBarMessage(snackBarHostState, "신고가 접수되었습니다.")
            navController.popBackStack()
        }
    }

    ReportsScreen(
        snackBarHostState = snackBarHostState,
        state = state,
        typeId = typeId,
        onNavigationClick = { navController.popBackStack()},
        onAction = { action ->
            when(action){
                is ReportsAction.OnChangeInputText -> { reportsViewModel.onAction(action)}
                is ReportsAction.OnAllDeleteInputText -> { reportsViewModel.onAction(action)}
                is ReportsAction.OnReports -> { reportsViewModel.onAction(action)}
            }
        }
    )
}

@Composable
private fun ReportsScreen(
    snackBarHostState: SnackbarHostState,
    state: ReportsState,
    typeId: Int,
    onNavigationClick: () -> Unit,
    onAction: (ReportsAction) -> Unit,
) {
    val focusManager = LocalFocusManager.current

    Scaffold(
        snackbarHost = {
            SnackbarHost(
                hostState = snackBarHostState,
            )
        },
        topBar = {
            ReportsScaffoldArea(
                onNavigationClick = onNavigationClick
            )
        }
    ){
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
                ReportsTitleArea()
                HeightSpacer(heightDp = 20.dp)
                ReportsInputReasonArea(
                    inputText = state.inputReason,
                    placeHolder = "신고 내용은 검토 후에 처리돼요.\n허위 신고 시 이용에 제한 될 수 있어요.",
                    onValueChange = { inputText -> onAction(ReportsAction.OnChangeInputText(inputText))},
                    onAllDeleteInputText = { onAction(ReportsAction.OnAllDeleteInputText)},
                )
            }

            ReportsButton(
                inputReason = state.inputReason,
                onClick = { onAction(ReportsAction.OnReports(typeId = typeId))}
            )
            HeightSpacer(heightDp = 12.dp)
        }
    }
}