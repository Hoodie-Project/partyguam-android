package com.party.presentation.screen.home_detail_profile.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.play.integrity.internal.s
import com.party.core.domain.DataErrorRemote
import com.party.core.domain.onError
import com.party.core.domain.onSuccess
import com.party.domain.model.user.detail.InterestLocationList
import com.party.domain.model.user.detail.InterestLocationRequest
import com.party.domain.model.user.detail.PersonalitySaveRequest
import com.party.domain.model.user.detail.PersonalitySaveRequest2
import com.party.domain.model.user.detail.SaveCarrierList
import com.party.domain.model.user.detail.SaveCarrierRequest
import com.party.domain.usecase.user.detail.GetLocationListUseCaseV2
import com.party.domain.usecase.user.detail.GetPersonalityUseCaseV2
import com.party.domain.usecase.user.detail.GetPositionsUseCaseV2
import com.party.domain.usecase.user.detail.SaveCareerUseCase
import com.party.domain.usecase.user.detail.SaveInterestLocationUseCaseV2
import com.party.domain.usecase.user.detail.SavePersonalityUseCase
import com.party.domain.usecase.user.detail.SavePersonalityUseCaseV2
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
    private val getPositionsUseCase: GetPositionsUseCaseV2,
    private val saveCareerUseCase: SaveCareerUseCase,
    private val getPersonalityUseCaseV2: GetPersonalityUseCaseV2,
    private val savePersonalityUseCase: SavePersonalityUseCaseV2,
): ViewModel() {

    private val _state = MutableStateFlow(HomeDetailProfileState())
    val state: StateFlow<HomeDetailProfileState> = _state.asStateFlow()

    private val _existSavedData = MutableSharedFlow<Unit>()
    val existSavedData = _existSavedData.asSharedFlow()

    private val _locationLimitExceeded = MutableSharedFlow<String>()
    val locationLimitExceeded = _locationLimitExceeded.asSharedFlow()

    private val _successSaveInterestLocation = MutableSharedFlow<Unit>()
    val successSaveInterestLocation = _successSaveInterestLocation.asSharedFlow()

    private val _successSaveCareer = MutableSharedFlow<Unit>()
    val successSaveCareer = _successSaveCareer.asSharedFlow()

    private val _traitLimitExceeded = MutableSharedFlow<String>()
    val traitLimitExceeded = _traitLimitExceeded.asSharedFlow()

    init {
        getPersonalityList()
    }


    // 서브 지역 리스트 조회
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

    // 관심지역 저장
    fun saveInterestLocation(){
        viewModelScope.launch(Dispatchers.IO) {
            val locations = InterestLocationList(
                locations = _state.value.selectedProvinceAndSubLocationList.map { InterestLocationRequest(id = it.second.id) }
            )
            saveInterestLocationUseCase(locations = locations)
                .onSuccess {
                    _successSaveInterestLocation.emit(Unit)
                }
                .onError { error ->
                    when(error){
                        is DataErrorRemote.Conflict -> { _existSavedData.emit(Unit)}
                        else -> {}
                    }
                }
        }
    }

    // 서브 포지션 조회
    fun getPositions(main: String){
        viewModelScope.launch(Dispatchers.IO) {
            getPositionsUseCase(
                main = main
            )
                .onSuccess { result ->
                    _state.update { it.copy(subPositionList = result) }
                }
                .onError {

                }
        }
    }

    // 커리어 저장
    fun saveCareer(){
        viewModelScope.launch(Dispatchers.IO) {
            val career = SaveCarrierList(
                listOf(
                    SaveCarrierRequest(
                        positionId = _state.value.firstSubPositionId,
                        years = convertToIntFromYear(_state.value.firstCareer),
                        careerType = "primary"
                    )
                )
            )
            saveCareerUseCase(career = career)
                .onSuccess {
                    _successSaveCareer.emit(Unit)
                }
                .onError { error ->
                    when(error){
                        is DataErrorRemote.Conflict -> { _existSavedData.emit(Unit)}
                        else -> {}
                    }
                }
        }
    }

    // 전체 성향 질문 조회
    fun getPersonalityList(){
        viewModelScope.launch(Dispatchers.IO) {
            getPersonalityUseCaseV2()
                .onSuccess { result ->
                    _state.update { it.copy(personalityList = result) }
                }
                .onError {  }
        }
    }

    // 전체 성향 질문 저장
    fun savePersonality(){
        viewModelScope.launch(Dispatchers.IO) {
            val personalitySaveRequest = PersonalitySaveRequest(
                personality = listOf(
                    PersonalitySaveRequest2(
                        personalityQuestionId = _state.value.selectedTraitList1[0].personalityQuestionId,
                        personalityOptionId = _state.value.selectedTraitList1.map { it.id }
                    ),
                    PersonalitySaveRequest2(
                        personalityQuestionId = _state.value.selectedTraitList2[0].personalityQuestionId,
                        personalityOptionId = _state.value.selectedTraitList2.map { it.id }
                    ),
                    PersonalitySaveRequest2(
                        personalityQuestionId = _state.value.selectedTraitList3[0].personalityQuestionId,
                        personalityOptionId = _state.value.selectedTraitList3.map { it.id }
                    ),
                    PersonalitySaveRequest2(
                        personalityQuestionId = _state.value.selectedTraitList4[0].personalityQuestionId,
                        personalityOptionId = _state.value.selectedTraitList4.map { it.id }
                    )
                )
            )
            savePersonalityUseCase(personalitySaveRequest = personalitySaveRequest)
                .onSuccess {

                }
                .onError {  }
        }
    }

    fun onAction(action: HomeDetailProfileAction){
        when(action){
            is HomeDetailProfileAction.OnShowFinishDialog -> _state.update { it.copy(isShowFinishDialog = action.isShow) }
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
            is HomeDetailProfileAction.OnClickFirstCareer -> _state.update { it.copy(firstCareer = action.career) }
            is HomeDetailProfileAction.OnClickFirstMainPosition -> {
                _state.update { it.copy(firstMainPosition = action.mainPosition) }
                getPositions(
                    main = action.mainPosition
                )
            }
            is HomeDetailProfileAction.OnClickFirstSubPosition -> _state.update { it.copy(firstSubPosition = action.positionList.sub, firstSubPositionId = action.positionList.id) }
            is HomeDetailProfileAction.OnResetFirst -> {
                _state.update {
                    it.copy(
                        firstCareer = "",
                        firstMainPosition = "",
                        firstSubPosition = "",
                        firstSubPositionId = 0
                    )
                }
            }

            is HomeDetailProfileAction.OnClickSecondCareer -> _state.update { it.copy(secondCareer = action.career) }
            is HomeDetailProfileAction.OnClickSecondMainPosition -> {
                _state.update { it.copy(secondMainPosition = action.mainPosition) }
                getPositions(
                    main = action.mainPosition
                )
            }
            is HomeDetailProfileAction.OnClickSecondSubPosition -> _state.update { it.copy(secondSubPosition = action.positionList.sub, secondSubPositionId = action.positionList.id) }
            is HomeDetailProfileAction.OnResetSecond -> {
                _state.update {
                    it.copy(
                        secondCareer = "",
                        secondMainPosition = "",
                        secondSubPosition = "",
                        secondSubPositionId = 0
                    )
                }
            }

            is HomeDetailProfileAction.OnSelectTrait1 -> {
                _state.update { state ->
                    val option = action.personalityListOption
                    val selectedList = state.selectedTraitList1

                    when {
                        selectedList.any { it.id == option.id } ->
                            state.copy(selectedTraitList1 = selectedList.filter { it.id != option.id })
                        selectedList.size >= 2 -> {
                            viewModelScope.launch { _traitLimitExceeded.emit("최대 2개까지 선택할 수 있어요.") }
                            state
                        }
                        else -> state.copy(selectedTraitList1 = selectedList + option)
                    }
                }
            }
            is HomeDetailProfileAction.OnSelectTrait2 -> {
                _state.update { state ->
                    val option = action.personalityListOption
                    val selectedList = state.selectedTraitList2

                    // 최대 1개만 선택 가능 (토글 방식)
                    if (selectedList.any { it.id == option.id }) {
                        // 이미 선택된 항목이면 제거
                        state.copy(selectedTraitList2 = emptyList())
                    } else {
                        // 새로운 항목 선택 (기존 선택 항목은 자동으로 교체)
                        state.copy(selectedTraitList2 = listOf(option))
                    }
                }
            }
            is HomeDetailProfileAction.OnSelectTrait3 -> {
                _state.update { state ->
                    val option = action.personalityListOption
                    val selectedList = state.selectedTraitList3

                    when {
                        selectedList.any { it.id == option.id } ->
                            state.copy(selectedTraitList3 = selectedList.filter { it.id != option.id })
                        selectedList.size >= 2 -> {
                            viewModelScope.launch { _traitLimitExceeded.emit("최대 2개까지 선택할 수 있어요.") }
                            state
                        }
                        else -> state.copy(selectedTraitList3 = selectedList + option)
                    }
                }
            }

            is HomeDetailProfileAction.OnSelectTrait4 -> {
                _state.update { state ->
                    val option = action.personalityListOption
                    val selectedList = state.selectedTraitList4

                    when {
                        selectedList.any { it.id == option.id } ->
                            state.copy(selectedTraitList4 = selectedList.filter { it.id != option.id })
                        selectedList.size >= 2 -> {
                            viewModelScope.launch { _traitLimitExceeded.emit("최대 2개까지 선택할 수 있어요.") }
                            state
                        }
                        else -> state.copy(selectedTraitList4 = selectedList + option)
                    }
                }
            }

        }
    }

    fun convertToIntFromYear(year: String): Int{
        return when(year){
            "신입" -> 0
            "1년" -> 1
            "2년" -> 2
            "3년" -> 3
            "4년" -> 4
            "5년" -> 5
            "6년" -> 6
            "7년" -> 7
            "8년" -> 8
            "9년" -> 9
            "10년" -> 10
            else -> 0
        }
    }
}