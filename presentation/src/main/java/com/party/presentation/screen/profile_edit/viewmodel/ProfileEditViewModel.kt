package com.party.presentation.screen.profile_edit.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.party.common.ServerApiResponse
import com.party.domain.usecase.user.profile.GetUserProfileUseCase
import com.party.presentation.screen.profile.UserProfileState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileEditViewModel @Inject constructor(
    private val getUserProfileUseCase: GetUserProfileUseCase,
): ViewModel(){

    private val _state = MutableStateFlow(UserProfileState())
    val state = _state.asStateFlow()

    fun getUserProfile() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            when(val result = getUserProfileUseCase()){
                is ServerApiResponse.SuccessResponse -> _state.update { it.copy(isLoading = false, userProfile = result.data ?: it.userProfile) }
                is ServerApiResponse.ErrorResponse -> _state.update { it.copy(isLoading = false) }
                is ServerApiResponse.ExceptionResponse -> _state.update { it.copy(isLoading = false) }
            }
        }
    }
}