package com.party.presentation.screen.home_detail_profile.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.party.core.domain.onError
import com.party.core.domain.onSuccess
import com.party.domain.model.user.detail.InterestLocationList
import com.party.domain.model.user.detail.InterestLocationRequest
import com.party.domain.usecase.user.detail.GetLocationListUseCaseV2
import com.party.domain.usecase.user.detail.SaveInterestLocationUseCaseV2
import com.party.presentation.screen.home_detail_profile.action.HomeDetailProfileAction
import com.party.presentation.screen.home_detail_profile.state.HomeDetailProfileState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeDetailProfileViewModel @Inject constructor(
    private val getLocationListUseCase: GetLocationListUseCaseV2,
    private val saveInterestLocationUseCase: SaveInterestLocationUseCaseV2,
): ViewModel() {

    private val _state = MutableStateFlow(HomeDetailProfileState())
    val state: StateFlow<HomeDetailProfileState> = _state.asStateFlow()

    private val _locationLimitExceeded = MutableSharedFlow<String>()
    val locationLimitExceeded = _locationLimitExceeded.asSharedFlow()

    private val _successSaveInterestLocation = MutableSharedFlow<Unit>()
    val successSaveInterestLocation = _successSaveInterestLocation.asSharedFlow()

    fun getLocationList(){
        viewModelScope.launch(Dispatchers.IO) {
            getLocationListUseCase(
                province = _state.value.selectedProvince
            )
                .onSuccess { result ->
                    _state.update { it.copy(subLocationList = result) }
                }
                .onError {  }
        }
    }

    fun saveInterestLocation(){
        viewModelScope.launch(Dispatchers.IO) {
            val locations = InterestLocationList(
                locations = _state.value.selectedProvinceAndSubLocationList.map { InterestLocationRequest(id = it.second.id) }
            )
            saveInterestLocationUseCase(locations = locations)
                .onSuccess {
                    _successSaveInterestLocation.emit(Unit)
                }
                .onError {  }
        }
    }

    fun onAction(action: HomeDetailProfileAction){
        when(action){
            is HomeDetailProfileAction.OnClickProvince -> _state.update { it.copy(selectedProvince = action.provinceName) }
            is HomeDetailProfileAction.OnClickSubLocation -> {
                _state.update {
                    val updatedList = it.selectedProvinceAndSubLocationList.toMutableList()

                    // 이미 같은 location이 있는지 확인
                    val existingIndex = updatedList.indexOfFirst { pair -> pair.second == action.location }

                    if (existingIndex != -1) {
                        // 이미 있으면 제거 (해제)
                        updatedList.removeAt(existingIndex)
                    } else {
                        // 없으면 추가 (단, 최대 3개까지)
                        if (updatedList.size >= 3) {
                            // 최대 3개 초과 시 알림
                            viewModelScope.launch {
                                _locationLimitExceeded.emit("최대 3개까지만 선택 가능합니다.")
                            }
                            return@update it // 상태 변경 없이 현재 상태 유지
                        } else {
                            val newPair = Pair(it.selectedProvince, action.location)
                            updatedList.add(newPair)
                        }
                    }

                    it.copy(selectedProvinceAndSubLocationList = updatedList)
                }
            }
            is HomeDetailProfileAction.OnDeleteSelectedLocation -> {
                _state.update {
                    val updatedList = it.selectedProvinceAndSubLocationList.toMutableList()
                    updatedList.remove(action.locationPair)
                    it.copy(selectedProvinceAndSubLocationList = updatedList)
                }
            }
        }
    }
}