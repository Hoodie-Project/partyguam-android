package com.party.presentation.screen.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.party.domain.usecase.datastore.GetAccessTokenUseCase
import com.party.domain.usecase.datastore.GetFirstLaunchFlagUseCase
import com.party.domain.usecase.datastore.SaveFcmTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getAccessTokenUseCase: GetAccessTokenUseCase,
    private val getFirstLaunchFlagUseCase: GetFirstLaunchFlagUseCase,
    private val saveFcmTokenUseCase: SaveFcmTokenUseCase,
): ViewModel() {

    private val _accessToken = MutableStateFlow("")
    val accessToken: StateFlow<String> = _accessToken

    val firstLaunchFlag = getFirstLaunchFlagUseCase()
        .stateIn(viewModelScope, SharingStarted.Eagerly, true)

    fun getAccessToken() = viewModelScope.launch(Dispatchers.IO) {
        getAccessTokenUseCase().collectLatest { accessToken ->
            _accessToken.emit(accessToken)
        }
    }

    fun saveFcmToken(token: String){
        viewModelScope.launch(Dispatchers.IO) {
            saveFcmTokenUseCase(token = token)
        }
    }

    private var _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    init {
        viewModelScope.launch {
            delay(2.seconds)
            _isLoading.value = false
        }
    }
}