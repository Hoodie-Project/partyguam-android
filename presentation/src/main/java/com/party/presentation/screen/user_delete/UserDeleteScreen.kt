package com.party.presentation.screen.user_delete

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.party.common.Screens
import com.party.common.component.dialog.TwoButtonDialog
import com.party.common.component.snackbar.CustomSnackBar
import com.party.common.utils.HeightSpacer
import com.party.common.utils.noRippleClickable
import com.party.guam.design.BLACK
import com.party.guam.design.MEDIUM_PADDING_SIZE
import com.party.guam.design.WHITE
import com.party.presentation.screen.user_delete.component.UserDeleteButton
import com.party.presentation.screen.user_delete.component.UserDeleteScaffoldArea
import com.party.presentation.screen.user_delete.component.WarningAgreeArea
import com.party.presentation.screen.user_delete.component.WarningArea
import com.party.presentation.screen.user_delete.component.WarningTitleArea
import com.party.presentation.screen.user_delete.viewmodel.UserDeleteViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun UserDeleteScreenRoute(
    navController: NavHostController,
    snackBarHostState: SnackbarHostState,
    onGotoLogin: () -> Unit,
    userDeleteViewModel: UserDeleteViewModel = hiltViewModel()
) {
    val userDeleteState by userDeleteViewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = Unit) {
        userDeleteViewModel.successSignOut.collectLatest {
            onGotoLogin()
        }
    }

    LaunchedEffect(key1 = Unit) {
        userDeleteViewModel.errorSignOut.collectLatest { snackBarMessage ->
            snackBarHostState.showSnackbar(snackBarMessage)
        }
    }

    UserDeleteScreen(
        snackBarHostState = snackBarHostState,
        userDeleteState = userDeleteState,
        onNavigationBack = { navController.popBackStack() },
        onAction = { action -> userDeleteViewModel.onAction(action = action) }
    )
}

@Composable
private fun UserDeleteScreen(
    snackBarHostState: SnackbarHostState,
    userDeleteState: UserDeleteState,
    onNavigationBack: () -> Unit,
    onAction: (UserDeleteAction) -> Unit
) {
    val scrollState = rememberScrollState()
    var isChecked by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Scaffold(
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
            modifier = Modifier
                .blur(
                    radiusX = if (userDeleteState.isShowUserDeleteDialog) 10.dp else 0.dp,
                    radiusY = if (userDeleteState.isShowUserDeleteDialog) 10.dp else 0.dp,
                ),
            topBar = {
                UserDeleteScaffoldArea(
                    onNavigationBack = onNavigationBack
                )
            }
        ){
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(WHITE)
                    .padding(it)
                    .padding(horizontal = MEDIUM_PADDING_SIZE)
                    .verticalScroll(scrollState)
            ) {
                // 타이틀
                HeightSpacer(heightDp = 16.dp)
                WarningTitleArea()

                // 회원탈퇴 유의사항
                HeightSpacer(heightDp = 24.dp)
                WarningArea()

                // Agree
                HeightSpacer(heightDp = 40.dp)
                WarningAgreeArea(
                    isChecked = isChecked,
                    onCheckedChange = { checked -> isChecked = checked }
                )

                // Delete Button
                HeightSpacer(heightDp = 40.dp)
                UserDeleteButton(
                    isChecked = isChecked,
                    onClick = {
                        if(isChecked){
                            onAction(UserDeleteAction.OnShowDeleteDialog(isShow = true))
                        }
                    }
                )
                HeightSpacer(heightDp = 10.dp)
            }
        }

        if(userDeleteState.isShowUserDeleteDialog){
            TwoButtonDialog(
                dialogTitle = "회원탈퇴",
                description = "탈퇴 시, 계정은 삭제되며 복구되지 않아요.\n정말 탈퇴하시겠어요?",
                cancelButtonText = "취소",
                confirmButtonText = "탈퇴하기",
                onCancel = {
                    onAction(UserDeleteAction.OnShowDeleteDialog(false))
                },
                onConfirm = {
                    onAction(UserDeleteAction.OnShowDeleteDialog(false))
                    onAction(UserDeleteAction.OnDelete)
                }
            )
        }

        if(userDeleteState.isShowUserDeleteDialog){
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(BLACK.copy(alpha = 0.2f))
                    .noRippleClickable { onAction(UserDeleteAction.OnShowDeleteDialog(false)) }
                    .zIndex(0f)
            )
        }
    }

}

@Preview(showBackground = true)
@Composable
fun UserDeleteScreenPreview() {
    UserDeleteScreen(
        snackBarHostState = SnackbarHostState(),
        userDeleteState = UserDeleteState(),
        onNavigationBack = {},
        onAction = {}
    )
}