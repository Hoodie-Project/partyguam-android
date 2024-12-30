package com.party.presentation.screen.profile_edit_locations.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.party.common.ServerApiResponse
import com.party.domain.model.user.detail.InterestLocationList
import com.party.domain.model.user.detail.InterestLocationRequest
import com.party.domain.usecase.user.detail.DeleteUserLocationUseCase
import com.party.domain.usecase.user.detail.GetLocationListUseCase
import com.party.domain.usecase.user.detail.SaveInterestLocationUseCase
import com.party.presentation.screen.profile_edit_locations.ProfileEditLocationAction
import com.party.presentation.screen.profile_edit_locations.ProfileEditLocationState
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
class ProfileEditLocationViewModel @Inject constructor(
    private val getLocationListUseCase: GetLocationListUseCase,
    private val saveInterestLocationUseCase: SaveInterestLocationUseCase,
    private val deleteUserLocationUseCase: DeleteUserLocationUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(ProfileEditLocationState())
    val state = _state.asStateFlow()

    private val _saveSuccess = MutableSharedFlow<Unit>()
    val saveSuccess = _saveSuccess.asSharedFlow()

    private val _fourth = MutableSharedFlow<Unit>()
    val fourth = _fourth.asSharedFlow()

    init {
        getLocationList("서울")
    }

    private fun fourthWarning() {
        viewModelScope.launch {
            _fourth.emit(Unit)
        }
    }

    private fun getLocationList(province: String) {
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = getLocationListUseCase(province = province)) {
                is ServerApiResponse.SuccessResponse -> {
                    _state.update { it.copy(getLocationList = result.data ?: emptyList()) }
                }

                is ServerApiResponse.ErrorResponse -> {}
                is ServerApiResponse.ExceptionResponse -> {}
            }
        }
    }

    private fun deleteLocation(locations: InterestLocationList) {
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = deleteUserLocationUseCase()) {
                is ServerApiResponse.SuccessResponse -> saveInterestLocation(locations = locations)
                is ServerApiResponse.ErrorResponse -> {}
                is ServerApiResponse.ExceptionResponse -> {}
            }
        }
    }

    private fun saveInterestLocation(locations: InterestLocationList) {
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = saveInterestLocationUseCase(locations = locations)) {
                is ServerApiResponse.SuccessResponse -> _saveSuccess.emit(Unit)
                is ServerApiResponse.ErrorResponse -> {}
                is ServerApiResponse.ExceptionResponse -> {}
            }
        }
    }

    fun onAction(action: ProfileEditLocationAction) {
        when (action) {
            is ProfileEditLocationAction.OnSelectProvince -> {
                _state.update { it.copy(selectedProvince = action.province) }
                getLocationList(action.province)
            }

            is ProfileEditLocationAction.OnSelectLocation -> {
                _state.update {
                    val isAlreadySelected =
                        it.selectedLocationList.contains(action.location)

                    // 이미 선택된 경우 제거
                    val updatedSelectedLocationList = if (isAlreadySelected) {
                        it.selectedLocationList.filter { location -> location != action.location }
                    } else {
                        // 최대 3개까지만 허용
                        if (it.selectedLocationList.size < 3) {
                            it.selectedLocationList + action.location
                        } else {
                            fourthWarning()
                            it.selectedLocationList
                        }
                    }

                    // Province + Location 리스트 업데이트
                    val updatedSelectedProvinceAndLocationList = if (isAlreadySelected) {
                        it.selectedProvinceAndLocationList.filter { pair -> pair.second != action.location }
                    } else {
                        if (it.selectedLocationList.size < 3) {
                            it.selectedProvinceAndLocationList + Pair(
                                it.selectedProvince,
                                action.location
                            )
                        } else {
                            it.selectedProvinceAndLocationList // 추가하지 않음
                        }
                    }

                    // 상태 업데이트
                    it.copy(
                        selectedLocationList = updatedSelectedLocationList,
                        selectedProvinceAndLocationList = updatedSelectedProvinceAndLocationList
                    )
                }
            }

            is ProfileEditLocationAction.OnDeleteProvinceAndLocation -> {
                _state.update {
                    val updatedSelectedProvinceAndLocationList =
                        it.selectedProvinceAndLocationList.filter { pair ->
                            pair != action.selectedProvinceAndLocation // 선택된 항목 제거
                        }

                    val updatedSelectedLocationList =
                        it.selectedLocationList.filter { location ->
                            location != action.selectedProvinceAndLocation.second // 해당 Location 제거
                        }

                    it.copy(
                        selectedLocationList = updatedSelectedLocationList,
                        selectedProvinceAndLocationList = updatedSelectedProvinceAndLocationList
                    )
                }
            }

            is ProfileEditLocationAction.OnReset -> {
                _state.update {
                    it.copy(
                        selectedProvince = "",
                        selectedLocationList = emptyList(),
                        selectedProvinceAndLocationList = emptyList()
                    )
                }
            }

            is ProfileEditLocationAction.OnApply -> {
                deleteLocation(
                    locations = InterestLocationList(
                        locations = _state.value.selectedLocationList.map {
                            InterestLocationRequest(id = it.id)
                        }
                    )
                )
            }
        }
    }
}

