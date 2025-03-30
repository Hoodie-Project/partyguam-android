package com.party.presentation.screen.notification

import com.party.common.component.notificationTabList
import com.party.domain.model.user.Notification

data class NotificationState(
    val selectedTab: String = notificationTabList[0],

    val notification: Notification = Notification(
        nextCursor = 0,
        notifications = emptyList()
    ),
)
