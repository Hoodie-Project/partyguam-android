package com.party.presentation.screen.profile_edit_locations.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.party.common.ServerApiResponse
import com.party.core.domain.DataErrorRemote
import com.party.core.domain.onError
import com.party.core.domain.onSuccess
import com.party.domain.model.user.detail.InterestLocationList
import com.party.domain.model.user.detail.InterestLocationRequest
import com.party.domain.usecase.user.detail.DeleteUserLocationUseCase
import com.party.domain.usecase.user.detail.DeleteUserLocationUseCaseV2
import com.party.domain.usecase.user.detail.GetLocationListUseCase
import com.party.domain.usecase.user.detail.GetUserLikeLocationUseCase
import com.party.domain.usecase.user.detail.SaveInterestLocationUseCase
import com.party.domain.usecase.user.detail.SaveInterestLocationUseCaseV2
import com.party.presentation.screen.profile_edit_locations.ProfileEditLocationAction
import com.party.presentation.screen.profile_edit_locations.ProfileEditLocationState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
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
    private val saveInterestLocationUseCase: SaveInterestLocationUseCaseV2,
    private val deleteUserLocationUseCase: DeleteUserLocationUseCaseV2,
    private val getUserLikeLocationUseCase: GetUserLikeLocationUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(ProfileEditLocationState())
    val state = _state.asStateFlow()

    private val _saveSuccess = MutableSharedFlow<Unit>()
    val saveSuccess = _saveSuccess.asSharedFlow()

    private val _fourth = MutableSharedFlow<Unit>()
    val fourth = _fourth.asSharedFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getUserLikeLocation()
            delay(200L)
            getLocationList("서울")
        }
    }

    private fun fourthWarning() {
        viewModelScope.launch {
            _fourth.emit(Unit)
        }
    }

    private fun getUserLikeLocation() {
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = getUserLikeLocationUseCase()) {
                is ServerApiResponse.SuccessResponse -> {
                    val likeList = result.data ?: emptyList()

                    val locationMap = _state.value.getLocationList.associateBy { it.id }

                    val selectedList = likeList
                        .distinctBy { it.locationId } // 같은 locationId 중복 제거
                        .mapNotNull { userLike ->
                            locationMap[userLike.locationId]
                        }


                    val selectedProvinceAndLocationList = selectedList.map { location ->
                        location.province to location
                    }

                    _state.update {
                        it.copy(
                            //getUserLikeLocationList = likeList,
                            //selectedLocationList = selectedList,
                            selectedProvinceAndLocationList = selectedProvinceAndLocationList,
                        )
                    }
                }

                is ServerApiResponse.ErrorResponse -> {}
                is ServerApiResponse.ExceptionResponse -> {}
            }
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

    fun saveInterestLocation(locations: InterestLocationList){
        viewModelScope.launch(Dispatchers.IO) {
            saveInterestLocationUseCase(locations = locations)
                .onSuccess {
                    _saveSuccess.emit(Unit)
                }
                .onError { error ->
                    when(error){
                        is DataErrorRemote.Conflict -> { deleteLocation(locations = locations)}
                        else -> {}
                    }
                }
        }
    }

    fun deleteLocation(locations: InterestLocationList){
        viewModelScope.launch(Dispatchers.IO) {
            deleteUserLocationUseCase()
                .onSuccess {
                    saveInterestLocation(locations)
                }
                .onError { error ->
                    when(error){
                        is DataErrorRemote.ServerError -> saveInterestLocation(locations)
                        else -> {}
                    }
                }
        }
    }

    fun onAction(action: ProfileEditLocationAction) {
        when (action) {
            is ProfileEditLocationAction.OnSelectProvince -> {
                _state.update {
                    it.copy(selectedProvince = action.province)
                }
                getLocationList(action.province)
            }

            is ProfileEditLocationAction.OnSelectLocation -> {
                _state.update { state ->
                    val isAlreadySelected = state.selectedLocationList.contains(action.location)

                    // 1. Location 리스트 업데이트 (중복 제거 & 최대 3개 제한)
                    val updatedSelectedLocationList = if (isAlreadySelected) {
                        state.selectedLocationList - action.location
                    } else {
                        if (state.selectedLocationList.size < 3) {
                            state.selectedLocationList + action.location
                        } else {
                            fourthWarning() // 최대 3개 경고 처리
                            state.selectedLocationList
                        }
                    }

                    // 2. Province + Location 누적 리스트 관리 (중복 없이 저장)
                    val updatedProvinceAndLocationList = if (isAlreadySelected) {
                        state.selectedProvinceAndLocationList.filterNot {
                            it.second == action.location
                        }
                    } else {
                        if (state.selectedLocationList.size < 3 &&
                            state.selectedProvinceAndLocationList.none { it.second == action.location }
                        ) {
                            state.selectedProvinceAndLocationList + (state.selectedProvince to action.location)
                        } else {
                            state.selectedProvinceAndLocationList
                        }
                    }

                    // 3. 최종 업데이트
                    state.copy(
                        selectedLocationList = updatedSelectedLocationList,
                        selectedProvinceAndLocationList = updatedProvinceAndLocationList
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
                deleteLocation(
                    locations = InterestLocationList(
                        locations = _state.value.selectedLocationList.map {
                            InterestLocationRequest(id = it.id)
                        }
                    )
                )
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

