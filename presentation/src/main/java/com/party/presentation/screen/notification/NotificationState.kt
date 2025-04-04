package com.party.presentation.screen.notification

import com.party.common.component.notificationTabList
import com.party.domain.model.user.notification.Notification

data class NotificationState(
    val selectedTab: String = notificationTabList[0],

    val notification: Notification = Notification(
        nextCursor = 0,
        notifications = emptyList()
    ),

    val isShowDeleteBottomSheet: Boolean = false,

    // 바텀시트가 열린 알림 ID
    val selectedNotificationId: Int = 0,
)
