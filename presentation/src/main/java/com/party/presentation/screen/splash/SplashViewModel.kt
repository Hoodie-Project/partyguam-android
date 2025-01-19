package com.party.presentation.screen.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.party.domain.usecase.datastore.GetAccessTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getAccessTokenUseCase: GetAccessTokenUseCase,
): ViewModel() {

    private val _accessToken = MutableStateFlow("")
    val accessToken: StateFlow<String> = _accessToken

    fun getAccessToken() = viewModelScope.launch(Dispatchers.IO) {
        getAccessTokenUseCase().collectLatest { accessToken ->
            _accessToken.emit(accessToken)
        }
    }
}