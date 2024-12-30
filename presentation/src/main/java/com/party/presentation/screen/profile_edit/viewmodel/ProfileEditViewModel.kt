package com.party.presentation.screen.profile_edit.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.party.common.ServerApiResponse
import com.party.domain.model.user.party.MyParty
import com.party.domain.model.user.profile.UserProfileRequest
import com.party.domain.usecase.user.party.GetMyPartyUseCase
import com.party.domain.usecase.user.profile.GetUserProfileUseCase
import com.party.domain.usecase.user.profile.ModifyUserProfileUseCase
import com.party.presentation.screen.profile.UserProfileState
import com.party.presentation.screen.profile_edit.ProfileEditAction
import com.party.presentation.screen.profile_edit_time.ProfileEditTimeAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

@HiltViewModel
class ProfileEditViewModel @Inject constructor(
    private val getUserProfileUseCase: GetUserProfileUseCase,
    private val getMyPartyUseCase: GetMyPartyUseCase,
    private val modifyUserProfileUseCase: ModifyUserProfileUseCase,
): ViewModel(){

    private val _state = MutableStateFlow(UserProfileState())
    val state = _state.asStateFlow()

    private val _successState = MutableSharedFlow<Unit>()
    val successState = _successState.asSharedFlow()

    fun getUserProfile() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            when(val result = getUserProfileUseCase()){
                is ServerApiResponse.SuccessResponse -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            userProfile = result.data ?: it.userProfile,
                            isVisibleGender = result.data?.genderVisible ?: it.isVisibleGender,
                            isVisibleBirth = result.data?.birthVisible ?: it.isVisibleBirth
                        )
                    }
                }
                is ServerApiResponse.ErrorResponse -> _state.update { it.copy(isLoading = false) }
                is ServerApiResponse.ExceptionResponse -> _state.update { it.copy(isLoading = false) }
            }
        }
    }

    private fun modifyPortfolio(image: MultipartBody.Part?, isVisibleGender: Boolean, isVisibleBirth: Boolean){
        viewModelScope.launch {
            when(val result = modifyUserProfileUseCase(
                image = image,
                genderVisible = isVisibleGender,
                birthVisible = isVisibleBirth,
            )) {
                is ServerApiResponse.SuccessResponse -> { _successState.emit(Unit) }
                is ServerApiResponse.ErrorResponse -> {}
                is ServerApiResponse.ExceptionResponse -> {}
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

    fun onAction(action: ProfileEditAction){
        when(action){
            is ProfileEditAction.OnChangeImage -> _state.update { it.copy(image = action.image) }
            is ProfileEditAction.OnChangeGenderVisible -> _state.update { it.copy(isVisibleGender = !it.isVisibleGender) }
            is ProfileEditAction.OnChangeBirthVisible -> _state.update { it.copy(isVisibleBirth = !it.isVisibleBirth) }
            is ProfileEditAction.OnModify -> {
                modifyPortfolio(
                    isVisibleGender =  _state.value.isVisibleGender,
                    isVisibleBirth = _state.value.isVisibleBirth,
                    image = if( _state.value.image != null) _state.value.image else null,
                )
            }
        }
    }
}