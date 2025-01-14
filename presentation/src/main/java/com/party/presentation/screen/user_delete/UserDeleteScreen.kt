package com.party.presentation.screen.user_delete

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
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
import com.party.common.HeightSpacer
import com.party.common.component.dialog.TwoButtonDialog
import com.party.common.noRippleClickable
import com.party.common.ui.theme.BLACK
import com.party.common.ui.theme.MEDIUM_PADDING_SIZE
import com.party.common.ui.theme.WHITE
import com.party.navigation.Screens
import com.party.presentation.screen.auth_setting.AuthSettingAction
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
    userDeleteViewModel: UserDeleteViewModel = hiltViewModel()
) {
    val userDeleteState by userDeleteViewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = Unit) {
        userDeleteViewModel.successSignOut.collectLatest {
            navController.navigate(Screens.Login) {
                popUpTo(navController.graph.startDestinationId) { inclusive = true }
                launchSingleTop = true
            }
        }
    }
    UserDeleteScreen(
        userDeleteState = userDeleteState,
        onNavigationBack = { navController.popBackStack() },
        onAction = { action ->
            when(action) {
                is UserDeleteAction.OnShowDeleteDialog -> userDeleteViewModel.onAction(action)
                is UserDeleteAction.OnDelete -> userDeleteViewModel.onAction(action)
            }
        }
    )
}

@Composable
private fun UserDeleteScreen(
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
                        onAction(UserDeleteAction.OnShowDeleteDialog(isShow = true))
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
        userDeleteState = UserDeleteState(),
        onNavigationBack = {},
        onAction = {}
    )
}