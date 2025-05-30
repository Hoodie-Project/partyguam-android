package com.party.presentation.screen.notification

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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.party.common.component.bottomsheet.NotificationDeleteBottomSheet
import com.party.common.utils.snackBarMessage
import com.party.guam.design.WHITE
import com.party.presentation.screen.notification.component.NotificationDescriptionArea
import com.party.presentation.screen.notification.component.NotificationListArea
import com.party.presentation.screen.notification.component.NotificationScaffoldArea
import com.party.presentation.screen.notification.component.NotificationTabArea
import com.party.presentation.screen.notification.viewmodel.NotificationViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun NotificationScreenRoute(
    navController: NavHostController,
    snackBarHostState: SnackbarHostState,
    notificationViewModel: NotificationViewModel = hiltViewModel()
) {
    val state by notificationViewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = Unit) {
        notificationViewModel.successDelete.collectLatest {
            notificationViewModel.getNotifications(
                limit = 10,
                type = if(state.selectedTab == "전체") null else if(state.selectedTab == "파티 활동") "party" else "recruit"
            )

            snackBarMessage(snackBarHostState = snackBarHostState, message = "알림이 삭제되었어요.")
        }
    }

    LaunchedEffect(key1 = state.selectedTab) {
        notificationViewModel.getNotifications(
            limit = 10,
            type = if(state.selectedTab == "전체") null else if(state.selectedTab == "파티 활동") "party" else "recruit"
        )
    }

    NotificationScreen(
        state = state,
        onNavigationClick = { navController.popBackStack() },
        onAction = { action ->
            when(action){
                is NotificationAction.OnSelectTab -> { notificationViewModel.onAction(action = action)}
                is NotificationAction.OnReadNotification -> { notificationViewModel.onAction(action = action)}
                is NotificationAction.OnShowDeleteBottomSheet -> { notificationViewModel.onAction(action = action)}
                is NotificationAction.OnDeleteNotification -> { notificationViewModel.onAction(action = action)}
            }
        }
    )
}

@Composable
private fun NotificationScreen(
    state: NotificationState,
    onNavigationClick: () -> Unit,
    onAction: (NotificationAction) -> Unit,
) {
    Scaffold(
        topBar = {
            NotificationScaffoldArea(
                onNavigationClick = onNavigationClick
            )
        }
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(WHITE)
                .padding(it)
        ) {
            NotificationTabArea(
                selectedTabText = state.selectedTab,
                onSelectTab = { selectedTab ->
                    onAction(NotificationAction.OnSelectTab(tabText = selectedTab))
                }
            )

            NotificationDescriptionArea()

            NotificationListArea(
                onClickNotificationItem = { selectedNotificationId ->
                    onAction(NotificationAction.OnReadNotification(notificationId = selectedNotificationId))
                },
                notification = state.notification,
                onDeleteNotification = { selectedNotificationId ->
                    onAction(NotificationAction.OnShowDeleteBottomSheet(isShow = true, notificationId = selectedNotificationId))
                },
            )
        }
    }

    if(state.isShowDeleteBottomSheet){
        NotificationDeleteBottomSheet(
            onBottomSheetClose = {
                onAction(NotificationAction.OnShowDeleteBottomSheet(isShow = false, notificationId = 0))
            },
            onDeleteNotification = {
                onAction(NotificationAction.OnDeleteNotification)
            }
        )
    }
}