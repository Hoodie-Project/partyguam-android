package com.party.presentation.screen.notification

sealed interface NotificationAction {
    data class OnSelectTab(val tabText: String): NotificationAction
    data class OnReadNotification(val notificationId: Int): NotificationAction
}