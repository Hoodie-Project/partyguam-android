package com.party.presentation.screen.detail.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.party.core.domain.DataErrorRemote
import com.party.core.domain.onError
import com.party.core.domain.onSuccess
import com.party.domain.model.user.detail.InterestLocationList
import com.party.domain.model.user.detail.InterestLocationRequest
import com.party.domain.model.user.detail.PersonalitySaveRequest
import com.party.domain.model.user.detail.PersonalitySaveRequest2
import com.party.domain.model.user.detail.SaveCarrierList
import com.party.domain.model.user.detail.SaveCarrierRequest
import com.party.domain.usecase.datastore.GetNickNameUseCase
import com.party.domain.usecase.user.detail.GetLocationListUseCaseV2
import com.party.domain.usecase.user.detail.GetPersonalityUseCaseV2
import com.party.domain.usecase.user.detail.GetPositionsUseCaseV2
import com.party.domain.usecase.user.detail.SaveCareerUseCase
import com.party.domain.usecase.user.detail.SaveInterestLocationUseCaseV2
import com.party.domain.usecase.user.detail.SavePersonalityUseCaseV2
import com.party.presentation.screen.detail.action.DetailProfileAction
import com.party.presentation.screen.detail.state.DetailProfileState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailProfileViewModel @Inject constructor(
    private val getLocationListUseCase: GetLocationListUseCaseV2,
    private val saveInterestLocationUseCase: SaveInterestLocationUseCaseV2,
    private val getPositionsUseCase: GetPositionsUseCaseV2,
    private val saveCareerUseCase: SaveCareerUseCase,
    private val getPersonalityUseCaseV2: GetPersonalityUseCaseV2,
    private val savePersonalityUseCase: SavePersonalityUseCaseV2,
    private val getNickNameUseCase: GetNickNameUseCase,
): ViewModel() {

    private val _state = MutableStateFlow(DetailProfileState())
    val state: StateFlow<DetailProfileState> = _state.asStateFlow()

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
        getNickName()
    }

    fun onResetSubPositionList(isMain: Boolean){
        if(isMain){
            if(_state.value.firstMainPosition.isEmpty()){
                _state.update { it.copy(subPositionListFirst = emptyList()) }
            }
        } else {
            if(_state.value.secondMainPosition.isEmpty()){
                _state.update { it.copy(subPositionListSecond = emptyList()) }
            }
        }
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
    fun getPositions(main: String, isMain: Boolean){
        viewModelScope.launch(Dispatchers.IO) {
            getPositionsUseCase(
                main = main
            )
                .onSuccess { result ->
                    if(isMain){
                        _state.update { it.copy(subPositionListFirst = result) }
                    } else {
                        _state.update { it.copy(subPositionListSecond = result) }
                    }

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
            val state = _state.value

            // 빈 리스트가 있는지 체크
            if (state.selectedTraitList1.isEmpty() ||
                state.selectedTraitList2.isEmpty() ||
                state.selectedTraitList3.isEmpty() ||
                state.selectedTraitList4.isEmpty()) {
                // 에러 처리 또는 사용자에게 알림
                return@launch
            }

            val personalitySaveRequest = PersonalitySaveRequest(
                personality = listOf(
                    PersonalitySaveRequest2(
                        personalityQuestionId = state.selectedTraitList1[0].personalityQuestionId,
                        personalityOptionId = state.selectedTraitList1.map { it.id }
                    ),
                    PersonalitySaveRequest2(
                        personalityQuestionId = state.selectedTraitList2[0].personalityQuestionId,
                        personalityOptionId = state.selectedTraitList2.map { it.id }
                    ),
                    PersonalitySaveRequest2(
                        personalityQuestionId = state.selectedTraitList3[0].personalityQuestionId,
                        personalityOptionId = state.selectedTraitList3.map { it.id }
                    ),
                    PersonalitySaveRequest2(
                        personalityQuestionId = state.selectedTraitList4[0].personalityQuestionId,
                        personalityOptionId = state.selectedTraitList4.map { it.id }
                    )
                )
            )

            savePersonalityUseCase(personalitySaveRequest = personalitySaveRequest)
                .onSuccess {
                    // 성공 처리
                }
                .onError {
                    // 에러 처리
                }
        }
    }

    fun getNickName(){
        viewModelScope.launch(Dispatchers.IO) {
            val nickName = getNickNameUseCase().firstOrNull()
            if(nickName != null){
                _state.update { it.copy(userNickName = nickName) }
            }
        }
    }


    fun onAction(action: DetailProfileAction){
        when(action){
            is DetailProfileAction.OnShowFinishDialog -> _state.update { it.copy(isShowFinishDialog = action.isShow) }
            is DetailProfileAction.OnClickProvince -> _state.update { it.copy(selectedProvince = action.provinceName) }
            is DetailProfileAction.OnClickSubLocation -> {
                _state.update { currentState ->
                    val currentProvince = currentState.selectedProvince
                    val isWhole = action.location.city == "전체"
                    val updatedList = currentState.selectedProvinceAndSubLocationList.toMutableList()

                    if (isWhole) {
                        // 현재 도/시의 모든 선택 제거 후 전체 토글
                        // 먼저 현재 province의 선택들을 제거
                        updatedList.removeAll { it.first == currentProvince }

                        // 이미 해당 province의 전체가 선택되어 있었는지 확인
                        val alreadyWholeSelected = currentState.selectedProvinceAndSubLocationList.any {
                            it.first == currentProvince && it.second.city == "전체"
                        }

                        if (!alreadyWholeSelected) {
                            // 전체가 선택되어 있지 않았다면 전체를 추가
                            updatedList.add(Pair(currentProvince, action.location))
                        }

                        return@update currentState.copy(selectedProvinceAndSubLocationList = updatedList)
                    } else {
                        // 특정 구/군 선택: 현재 province의 전체를 해제하고 해당 구/군을 토글
                        // 현재 province의 전체 선택 제거
                        updatedList.removeAll { it.first == currentProvince && it.second.city == "전체" }

                        // 동일 항목이 이미 있는지 확인 (토글)
                        val existingIndex = updatedList.indexOfFirst { pair -> pair.first == currentProvince && pair.second == action.location }
                        if (existingIndex != -1) {
                            updatedList.removeAt(existingIndex)
                            return@update currentState.copy(selectedProvinceAndSubLocationList = updatedList)
                        }

                        // 새 항목 추가 (글로벌 최대 3개 제한)
                        if (updatedList.size >= 3) {
                            viewModelScope.launch {
                                _locationLimitExceeded.emit("최대 3개까지만 선택 가능합니다.")
                            }
                            return@update currentState
                        }

                        updatedList.add(Pair(currentProvince, action.location))
                        return@update currentState.copy(selectedProvinceAndSubLocationList = updatedList)
                    }
                }
            }
            is DetailProfileAction.OnDeleteSelectedLocation -> {
                _state.update {
                    val updatedList = it.selectedProvinceAndSubLocationList.toMutableList()
                    updatedList.remove(action.locationPair)
                    it.copy(selectedProvinceAndSubLocationList = updatedList)
                }
            }
            is DetailProfileAction.OnClickFirstCareer -> _state.update { it.copy(firstCareer = action.career) }
            is DetailProfileAction.OnClickFirstMainPosition -> {
                _state.update { it.copy(firstMainPosition = action.mainPosition) }
                getPositions(
                    main = action.mainPosition,
                    isMain = true,
                )
            }
            is DetailProfileAction.OnClickFirstSubPosition -> _state.update { it.copy(firstSubPosition = action.positionList.sub, firstSubPositionId = action.positionList.id) }
            is DetailProfileAction.OnResetFirst -> {
                _state.update {
                    it.copy(
                        firstCareer = "",
                        firstMainPosition = "",
                        firstSubPosition = "",
                        firstSubPositionId = 0
                    )
                }
            }

            is DetailProfileAction.OnClickSecondCareer -> _state.update { it.copy(secondCareer = action.career) }
            is DetailProfileAction.OnClickSecondMainPosition -> {
                _state.update { it.copy(secondMainPosition = action.mainPosition) }
                getPositions(
                    main = action.mainPosition,
                    isMain = false,
                )
            }
            is DetailProfileAction.OnClickSecondSubPosition -> _state.update { it.copy(secondSubPosition = action.positionList.sub, secondSubPositionId = action.positionList.id) }
            is DetailProfileAction.OnResetSecond -> {
                _state.update {
                    it.copy(
                        secondCareer = "",
                        secondMainPosition = "",
                        secondSubPosition = "",
                        secondSubPositionId = 0
                    )
                }
            }

            is DetailProfileAction.OnSelectTrait1 -> {
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
            is DetailProfileAction.OnSelectTrait2 -> {
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
            is DetailProfileAction.OnSelectTrait3 -> {
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

            is DetailProfileAction.OnSelectTrait4 -> {
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