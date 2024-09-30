package com.party.presentation.screen.join

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.party.common.ServerApiResponse
import com.party.common.UIState
import com.party.domain.usecase.user.CheckNickNameUseCase
import com.skydoves.sandwich.StatusCode
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JoinViewModel @Inject constructor(
    private val checkNickNameUseCase: CheckNickNameUseCase,
): ViewModel() {

    private val _checkNickNameState = MutableStateFlow<UIState<ServerApiResponse<Unit>>>(UIState.Idle)
    val checkNickNameState: StateFlow<UIState<ServerApiResponse<Unit>>> = _checkNickNameState

    fun checkNickName(
        signupAccessToken: String,
        nickname: String
    ){
        viewModelScope.launch(Dispatchers.IO) {
            _checkNickNameState.value = UIState.Loading
            when (val result = checkNickNameUseCase(signupAccessToken = signupAccessToken, nickname = nickname)) {
                is ServerApiResponse.SuccessResponse<Unit> -> {
                    //println("checkUserNickNameState 성공!!")
                    _checkNickNameState.value = UIState.Success(result)
                }
                is ServerApiResponse.ErrorResponse<*> -> {
                    //println("checkUserNickNameState 실패!! ${result.message}")
                    _checkNickNameState.value = UIState.Error(result)
                }
                is ServerApiResponse.ExceptionResponse -> { _checkNickNameState.value = UIState.Exception }
            }
        }
    }
}