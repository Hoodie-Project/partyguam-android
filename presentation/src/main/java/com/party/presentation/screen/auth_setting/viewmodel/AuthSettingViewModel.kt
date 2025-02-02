package com.party.presentation.screen.auth_setting.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.party.common.ServerApiResponse
import com.party.domain.model.user.AccessTokenRequest
import com.party.domain.model.user.LinkKakaoRequest
import com.party.domain.model.user.MySocialOauth
import com.party.domain.usecase.datastore.DeleteAccessTokenUseCase
import com.party.domain.usecase.user.UserLogoutUseCase
import com.party.domain.usecase.user.auth.LinkGoogleUseCase
import com.party.domain.usecase.user.auth.LinkKakaoUseCase
import com.party.domain.usecase.user.auth.MySocialOauthUseCase
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
    private val mySocialOauthUseCase: MySocialOauthUseCase,
    private val linkKakaoUseCase: LinkKakaoUseCase,
    private val linkGoogleUseCase: LinkGoogleUseCase,
): ViewModel(){

    private val _state = MutableStateFlow(AuthSettingState())
    val state = _state.asStateFlow()

    private val _successLogout = MutableSharedFlow<Unit>()
    val successLogout = _successLogout.asSharedFlow()

    init {
        mySocialOauth()
    }

    private fun mySocialOauth(){
        viewModelScope.launch(Dispatchers.IO) {
            when(val result = mySocialOauthUseCase()){
                is ServerApiResponse.SuccessResponse -> {
                    _state.update { it.copy(mySocialOauth = result.data ?: emptyList()) }
                }
                is ServerApiResponse.ErrorResponse -> {}
                is ServerApiResponse.ExceptionResponse -> {}
            }
        }
    }

    fun linkGoogle(accessToken: String){
        viewModelScope.launch(Dispatchers.IO) {
            when(val result = linkGoogleUseCase(accessTokenRequest = AccessTokenRequest(idToken = accessToken))){
                is ServerApiResponse.SuccessResponse -> mySocialOauth()
                is ServerApiResponse.ErrorResponse -> {}
                is ServerApiResponse.ExceptionResponse -> {}
            }
        }
    }

    fun linkKakao(oauthAccessToken: String){
        viewModelScope.launch(Dispatchers.IO) {
            when(val result = linkKakaoUseCase(
                linkKakaoRequest = LinkKakaoRequest(
                    oauthAccessToken = oauthAccessToken
                )
            )){
                is ServerApiResponse.SuccessResponse -> mySocialOauth()
                is ServerApiResponse.ErrorResponse -> {}
                is ServerApiResponse.ExceptionResponse -> {}
            }
        }
    }

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