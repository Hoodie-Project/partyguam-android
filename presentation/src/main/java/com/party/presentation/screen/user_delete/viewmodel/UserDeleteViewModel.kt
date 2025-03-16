package com.party.presentation.screen.user_delete.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.party.common.ServerApiResponse
import com.party.domain.usecase.datastore.DeleteAccessTokenUseCase
import com.party.domain.usecase.user.UserSignOutUseCase
import com.party.presentation.screen.user_delete.UserDeleteAction
import com.party.presentation.screen.user_delete.UserDeleteState
import com.skydoves.sandwich.StatusCode
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
class UserDeleteViewModel @Inject constructor(
    private val userSignOutUseCase: UserSignOutUseCase,
    private val deleteAccessTokenUseCase: DeleteAccessTokenUseCase,
): ViewModel(){

    private val _state = MutableStateFlow(UserDeleteState())
    val state = _state.asStateFlow()

    private val _successSignOut = MutableSharedFlow<Unit>()
    val successSignOut = _successSignOut.asSharedFlow()

    private val _errorSignOut = MutableSharedFlow<String>()
    val errorSignOut = _errorSignOut.asSharedFlow()

    private fun signOut(){
        viewModelScope.launch(Dispatchers.IO) {
            when(val result = userSignOutUseCase()){
                is ServerApiResponse.SuccessResponse -> {
                    val resultDeleteAccessToken = deleteAccessTokenUseCase()
                    if(resultDeleteAccessToken.isEmpty()){
                        _successSignOut.emit(Unit)
                    }
                }

                is ServerApiResponse.ErrorResponse -> {
                    when(result.statusCode){
                        StatusCode.Forbidden.code -> {
                            _errorSignOut.emit("파티장의 권한이 있어 탈퇴가 불가능 합니다.")
                        }
                    }
                }
                is ServerApiResponse.ExceptionResponse -> {}
            }
        }
    }

    fun onAction(action: UserDeleteAction){
        when(action){
            is UserDeleteAction.OnShowDeleteDialog -> _state.update { it.copy(isShowUserDeleteDialog = action.isShow)}
            is UserDeleteAction.OnDelete -> signOut()
        }
    }
}