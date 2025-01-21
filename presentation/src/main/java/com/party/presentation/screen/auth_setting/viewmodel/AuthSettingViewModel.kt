package com.party.presentation.screen.auth_setting.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.kakao.sdk.user.UserApiClient
import com.party.common.ServerApiResponse
import com.party.domain.usecase.datastore.DeleteAccessTokenUseCase
import com.party.domain.usecase.user.UserLogoutUseCase
import com.party.presentation.screen.auth_setting.AuthSettingAction
import com.party.presentation.screen.auth_setting.AuthSettingState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthSettingViewModel @Inject constructor(
    private val userLogoutUseCase: UserLogoutUseCase,
    private val deleteAccessTokenUseCase: DeleteAccessTokenUseCase,
): ViewModel(){

    private val _state = MutableStateFlow(AuthSettingState())
    val state = _state.asStateFlow()

    private val _successLogout = MutableSharedFlow<Unit>()
    val successLogout = _successLogout.asSharedFlow()

    private fun logout(){
        viewModelScope.launch(Dispatchers.IO) {
            when(val result = userLogoutUseCase()){
                is ServerApiResponse.SuccessResponse -> {
                    val resu = deleteAccessTokenUseCase()
                    if(resu == ""){
                        _successLogout.emit(Unit)
                    }
                }

                is ServerApiResponse.ErrorResponse -> {}
                is ServerApiResponse.ExceptionResponse -> {}
            }
        }
    }

    fun onAction(action: AuthSettingAction){
        when(action){
            is AuthSettingAction.OnShowLogoutDialog -> _state.update { it.copy(isShowLogoutDialog = action.isShow) }
            is AuthSettingAction.OnLogout -> logout()
        }
    }
}