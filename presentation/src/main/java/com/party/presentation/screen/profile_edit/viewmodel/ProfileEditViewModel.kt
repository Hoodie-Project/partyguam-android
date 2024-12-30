package com.party.presentation.screen.profile_edit.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.party.common.ServerApiResponse
import com.party.domain.model.user.party.MyParty
import com.party.domain.usecase.user.party.GetMyPartyUseCase
import com.party.domain.usecase.user.profile.GetUserProfileUseCase
import com.party.presentation.screen.profile.UserProfileState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileEditViewModel @Inject constructor(
    private val getUserProfileUseCase: GetUserProfileUseCase,
    private val getMyPartyUseCase: GetMyPartyUseCase,
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

    fun getMyParty(page: Int, limit: Int, sort: String, order: String){
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(isMyPartyLoading = true) }

            when(val result = getMyPartyUseCase(page, limit, sort, order)){
                is ServerApiResponse.SuccessResponse -> {
                    _state.update {
                        it.copy(
                            isMyPartyLoading = false,
                            myPartyList = result.data ?: MyParty(total = 0, partyUsers = emptyList())
                        )
                    }
                }
                is ServerApiResponse.ErrorResponse -> _state.update { it.copy(isMyPartyLoading = false) }
                is ServerApiResponse.ExceptionResponse -> _state.update { it.copy(isMyPartyLoading = false) }
            }
        }
    }
}