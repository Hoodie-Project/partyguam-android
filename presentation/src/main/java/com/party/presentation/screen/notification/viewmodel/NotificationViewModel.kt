package com.party.presentation.screen.notification.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.party.common.ServerApiResponse
import com.party.domain.model.user.notification.Notification
import com.party.domain.usecase.user.notification.GetNotificationUseCase
import com.party.domain.usecase.user.notification.ReadNotificationUseCase
import com.party.presentation.screen.notification.NotificationAction
import com.party.presentation.screen.notification.NotificationState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    val getNotificationUseCase: GetNotificationUseCase,
    val readNotificationUseCase: ReadNotificationUseCase,
): ViewModel() {

    private val _state = MutableStateFlow(NotificationState())
    val state: StateFlow<NotificationState> = _state.asStateFlow()

    fun getNotifications(
        limit: Int,
        type: String?,
    ){
        viewModelScope.launch(Dispatchers.IO) {
            when(val result = getNotificationUseCase(limit = limit, type = type)){
                is ServerApiResponse.SuccessResponse -> {
                    _state.update { it.copy(notification = result.data ?: Notification(nextCursor = 0, notifications = emptyList())) }
                }
                is ServerApiResponse.ErrorResponse -> {}
                is ServerApiResponse.ExceptionResponse -> {}
            }
        }
    }

    private fun readNotification(notificationId: Int){
        viewModelScope.launch(Dispatchers.IO) {
            when(val result = readNotificationUseCase(notificationId = notificationId)){
                is ServerApiResponse.SuccessResponse -> {
                    getNotifications(limit = 10, type = if(_state.value.selectedTab == "전체") null else if(_state.value.selectedTab == "파티활동") "party" else "recruit")
                }
                is ServerApiResponse.ErrorResponse -> {}
                is ServerApiResponse.ExceptionResponse -> {}
            }
        }
    }

    fun onAction(action: NotificationAction){
        when(action){
            is NotificationAction.OnSelectTab -> _state.update { it.copy(selectedTab = action.tabText) }
            is NotificationAction.OnReadNotification -> readNotification(notificationId = action.notificationId)
        }
    }
}