package com.party.presentation.screen.notification

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.party.common.ui.theme.WHITE
import com.party.presentation.screen.notification.component.NotificationDescriptionArea
import com.party.presentation.screen.notification.component.NotificationListArea
import com.party.presentation.screen.notification.component.NotificationScaffoldArea
import com.party.presentation.screen.notification.component.NotificationTabArea
import com.party.presentation.screen.notification.viewmodel.NotificationViewModel

@Composable
fun NotificationScreenRoute(
    navController: NavHostController,
    notificationViewModel: NotificationViewModel = hiltViewModel()
) {
    val state by notificationViewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = state.selectedTab) {
        notificationViewModel.getNotifications(
            limit = 10,
            type = if(state.selectedTab == "전체") null else if(state.selectedTab == "파티활동") "party" else "recruit"
        )
    }

    NotificationScreen(
        state = state,
        onNavigationClick = { navController.popBackStack() },
        onAction = { action ->
            when(action){
                is NotificationAction.OnSelectTab -> { notificationViewModel.onAction(action = action)}
                is NotificationAction.OnReadNotification -> { notificationViewModel.onAction(action = action)}
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
                notification = state.notification
            )
        }
    }
}