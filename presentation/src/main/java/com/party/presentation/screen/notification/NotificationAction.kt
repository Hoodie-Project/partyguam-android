package com.party.presentation.screen.notification

sealed interface NotificationAction {
    data class OnSelectTab(val tabText: String): NotificationAction
}