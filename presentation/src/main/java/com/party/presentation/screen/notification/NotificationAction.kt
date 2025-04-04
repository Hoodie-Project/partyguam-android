package com.party.presentation.screen.notification

sealed interface NotificationAction {
    data class OnSelectTab(val tabText: String): NotificationAction
    data class OnReadNotification(val notificationId: Int): NotificationAction
    data class OnShowDeleteBottomSheet(val isShow: Boolean, val notificationId: Int): NotificationAction
    data object OnDeleteNotification: NotificationAction
}