package com.party.presentation.screen.recover_auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.party.common.ServerApiResponse
import com.party.domain.model.user.SocialLogin
import com.party.domain.model.user.SocialLoginSuccess
import com.party.domain.usecase.datastore.SaveAccessTokenUseCase
import com.party.domain.usecase.user.RecoverAuthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecoverViewModel @Inject constructor(
    private val recoverAuthUseCase: RecoverAuthUseCase,
    private val saveAccessTokenUseCase: SaveAccessTokenUseCase,
): ViewModel() {

    private val _recoverAuthSuccess = MutableSharedFlow<Unit>()
    val recoverAuthSuccess = _recoverAuthSuccess.asSharedFlow()

    fun recoverAuth(recoverAccessToken: String){
        viewModelScope.launch(Dispatchers.IO) {
            when(val result = recoverAuthUseCase(recoverAccessToken = recoverAccessToken)){
                is ServerApiResponse.SuccessResponse<SocialLogin> -> {
                    val socialLoginResponse = result.data as SocialLoginSuccess
                    saveAccessToken(token = socialLoginResponse.accessToken)
                    _recoverAuthSuccess.emit(Unit)
                }
                is ServerApiResponse.ErrorResponse<*> -> {}
                is ServerApiResponse.ExceptionResponse<*> -> {}
            }
        }
    }

    private fun saveAccessToken(token: String){
        viewModelScope.launch(Dispatchers.IO) {
            saveAccessTokenUseCase(token = token)
        }
    }
}