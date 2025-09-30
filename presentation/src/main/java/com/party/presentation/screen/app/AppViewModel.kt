package com.party.presentation.screen.app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.party.common.component.homeTopTabList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow(AppState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            AppEvent.event.collect { flag ->
                if(flag){
                    showBlackBackground()
                } else {
                    hideBlackBackground()
                }
            }
        }
    }

    fun showBlackBackground() {
        _state.update { it.copy(isShowBlackBackground = true) }
    }
    fun hideBlackBackground() {
        _state.update { it.copy(isShowBlackBackground = false) }
    }

    fun selectTab(tabText: String) {
        _state.update { it.copy(selectedTabText = tabText) }
    }

    fun setCurrentScreen(currentScreen: String?){
        _state.update { it.copy(currentScreen = currentScreen) }
    }

    fun startScroll(flag: Boolean){
        _state.update { it.copy(isScrollStart = flag) }
    }

    fun startScrollParty(flag: Boolean){
        _state.update { it.copy(isScrollStartParty = flag) }
    }

    fun startScrollRecruitment(flag: Boolean){
        _state.update { it.copy(isScrollStartRecruitment = flag) }
    }
}

object AppEvent {
    private val _event = MutableStateFlow(false)
    val event: StateFlow<Boolean> = _event.asStateFlow()

    fun emit(isShow: Boolean) {
        _event.value = isShow
    }
}

data class AppState(
    val isShowBlackBackground: Boolean = false,

    // 메인 / 파티 / 모집공고 탭
    val selectedTabText: String = homeTopTabList[0],

    // 현재 화면 Home, State, Profile ...
    val currentScreen: String? = null,

    val isScrollStart: Boolean = false,
    val isScrollStartParty: Boolean = false,
    val isScrollStartRecruitment: Boolean = false,
)
