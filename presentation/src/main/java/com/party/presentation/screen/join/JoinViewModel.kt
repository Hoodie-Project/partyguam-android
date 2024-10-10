package com.party.presentation.screen.join

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.party.common.ServerApiResponse
import com.party.common.UIState
import com.party.domain.model.user.signup.UserSignUpRequest
import com.party.domain.model.user.signup.UserSignUpResponse
import com.party.domain.usecase.user.auth.CheckNickNameUseCase
import com.party.domain.usecase.user.auth.UserSignUpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JoinViewModel @Inject constructor(
    private val checkNickNameUseCase: CheckNickNameUseCase,
    private val userSignUpUseCase: UserSignUpUseCase,
): ViewModel() {

    private val _checkNickNameState = MutableStateFlow<UIState<ServerApiResponse<String>>>(UIState.Idle)
    val checkNickNameState: StateFlow<UIState<ServerApiResponse<String>>> = _checkNickNameState

    private val _userSignUpState =  MutableStateFlow<UIState<ServerApiResponse<UserSignUpResponse>>>(UIState.Idle)
    val userSignUpState: StateFlow<UIState<ServerApiResponse<UserSignUpResponse>>> = _userSignUpState

    fun checkNickName(
        signupAccessToken: String,
        nickname: String
    ){
        viewModelScope.launch(Dispatchers.IO) {
            _checkNickNameState.value = UIState.Loading
            when (val result = checkNickNameUseCase(signupAccessToken = signupAccessToken, nickname = nickname)) {
                is ServerApiResponse.SuccessResponse<String> -> {
                    _checkNickNameState.value = UIState.Success(result)
                }
                is ServerApiResponse.ErrorResponse<*> -> {
                    _checkNickNameState.value = UIState.Error(result)
                }
                is ServerApiResponse.ExceptionResponse -> { _checkNickNameState.value = UIState.Exception }
            }
        }
    }

    fun resetCheckNickNameState(){
        _checkNickNameState.value = UIState.Idle
    }

    fun userSignUp(
        signupAccessToken: String,
        userSignUpRequest: UserSignUpRequest,
    ){
        viewModelScope.launch(Dispatchers.IO) {
            _userSignUpState.value = UIState.Loading
            when(val result = userSignUpUseCase(signupAccessToken = signupAccessToken, userSignUpRequest = userSignUpRequest)){
                is ServerApiResponse.SuccessResponse<UserSignUpResponse> -> {
                    _userSignUpState.value = UIState.Success(result)
                }
                is ServerApiResponse.ErrorResponse<*> -> {
                    _userSignUpState.value = UIState.Error(result)
                }
                is ServerApiResponse.ExceptionResponse -> { _userSignUpState.value = UIState.Exception }
            }
        }
    }
}