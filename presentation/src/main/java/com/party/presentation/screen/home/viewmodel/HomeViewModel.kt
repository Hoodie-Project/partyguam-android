package com.party.presentation.screen.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.party.common.ServerApiResponse
import com.party.common.component.homeTopTabList
import com.party.domain.model.banner.Banner
import com.party.domain.model.party.PartyList
import com.party.domain.model.party.PersonalRecruitmentList
import com.party.domain.model.party.RecruitmentList
import com.party.domain.model.user.detail.PositionList
import com.party.domain.usecase.banner.GetBannerListUseCase
import com.party.domain.usecase.party.GetPartyListUseCase
import com.party.domain.usecase.party.GetPersonalRecruitmentListUseCase
import com.party.domain.usecase.party.GetRecruitmentListUseCase
import com.party.domain.usecase.user.detail.GetPositionsUseCase
import com.party.presentation.enum.OrderDescType
import com.party.presentation.enum.PartyType
import com.party.presentation.screen.home.HomeAction
import com.party.presentation.screen.home.HomeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getPersonalRecruitmentListUseCase: GetPersonalRecruitmentListUseCase,
    private val getRecruitmentListUseCase: GetRecruitmentListUseCase,
    private val getPartyListUseCase: GetPartyListUseCase,
    private val getPositionsUseCase: GetPositionsUseCase,
    private val getBannerListUseCase: GetBannerListUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state

    private val _scrollToUpParty = MutableSharedFlow<Unit>()
    val scrollToUpParty = _scrollToUpParty.asSharedFlow()

    private val _scrollToUpRecruitment = MutableSharedFlow<Unit>()
    val scrollToUpRecruitment = _scrollToUpRecruitment.asSharedFlow()

    init {
        getBannerList()
        getPersonalRecruitmentList(1, 50, "createdAt", OrderDescType.DESC.type)
    }

    fun updateFabVisibleParty(isScrollParty: Boolean){
        viewModelScope.launch {
            _state.update { it.copy(isScrollParty = isScrollParty) }
        }
    }

    fun updateFabVisibleRecruitment(isScrollRecruitment: Boolean){
        viewModelScope.launch {
            _state.update { it.copy(isScrollRecruitment = isScrollRecruitment) }
        }
    }

    private fun getPersonalRecruitmentList(
        page: Int,
        size: Int,
        sort: String,
        order: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(isLoadingPersonalRecruitmentList = true) }
            when (val result = getPersonalRecruitmentListUseCase(
                page = page,
                size = size,
                sort = sort,
                order = order
            )) {
                is ServerApiResponse.SuccessResponse<PersonalRecruitmentList> -> {
                    _state.update {
                        it.copy(
                            isLoadingPersonalRecruitmentList = false,
                            personalRecruitmentList = result.data ?: PersonalRecruitmentList(emptyList(), 0),
                            isNotProfileError = false
                        )
                    }
                }

                is ServerApiResponse.ErrorResponse -> _state.update { it.copy(isLoadingPersonalRecruitmentList = false, isNotProfileError = true) }
                is ServerApiResponse.ExceptionResponse -> _state.update { it.copy(isLoadingPersonalRecruitmentList = false) }
            }
        }
    }

    fun getRecruitmentList(
        page: Int,
        size: Int,
        sort: String,
        order: String,
        titleSearch: String?,
        partyTypes: List<Int> = emptyList(),
        position: List<Int> = emptyList(),
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(isLoadingRecruitmentList = true) }
            when (val result = getRecruitmentListUseCase(page = page, size = size, sort = sort, order = order, titleSearch = titleSearch, partyTypes = partyTypes, position = position)) {
                is ServerApiResponse.SuccessResponse<RecruitmentList> -> {
                    _state.update {
                        it.copy(
                            isLoadingRecruitmentList = false,
                            recruitmentList = result.data ?: RecruitmentList(emptyList(), 0)
                        )
                    }
                }

                is ServerApiResponse.ErrorResponse<RecruitmentList> -> _state.update { it.copy(isLoadingRecruitmentList = false) }
                is ServerApiResponse.ExceptionResponse -> _state.update { it.copy(isLoadingRecruitmentList = false) }
            }
        }
    }

    fun getPartyList(
        page: Int,
        size: Int,
        sort: String,
        order: String,
        partyTypes: List<Int> = emptyList(),
        titleSearch: String?,
        status: String?,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(isLoadingPartyList = true) }
            when (val result = getPartyListUseCase(
                page = page,
                size = size,
                sort = sort,
                order = order,
                partyTypes = partyTypes,
                titleSearch = titleSearch,
                status = status
            )) {
                is ServerApiResponse.SuccessResponse<PartyList> -> {
                    _state.update {
                        it.copy(
                            isLoadingPartyList = false,
                            partyList = result.data ?: PartyList(emptyList(), 0)
                        )
                    }
                }
                is ServerApiResponse.ErrorResponse<PartyList> -> _state.update { it.copy(isLoadingPartyList = false) }
                is ServerApiResponse.ExceptionResponse -> _state.update { it.copy(isLoadingPartyList = false) }
            }
        }
    }

    private fun getSubPositionList(main: String) {
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = getPositionsUseCase(main = main)) {
                is ServerApiResponse.SuccessResponse<List<PositionList>> -> { _state.update { state -> state.copy(selectedMainPosition = main, getSubPositionList = result.data ?: emptyList()) } }
                is ServerApiResponse.ErrorResponse<List<PositionList>> -> {}
                is ServerApiResponse.ExceptionResponse -> {}
            }
        }
    }

    private fun getBannerList() {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(isLoadingBanner = true) }
            when (val result = getBannerListUseCase()) {
                is ServerApiResponse.SuccessResponse<Banner> -> {
                    _state.update {
                        it.copy(
                            isLoadingBanner = false,
                            banner = result.data ?: Banner(0, emptyList())
                        )
                    }
                }

                is ServerApiResponse.ErrorResponse<Banner> -> _state.update { it.copy(isLoadingBanner = false) }
                is ServerApiResponse.ExceptionResponse -> _state.update { it.copy(isLoadingBanner = false) }
            }
        }
    }

    fun onAction(action: HomeAction) {
        when(action){
            is HomeAction.OnTabClick -> _state.update { it.copy(selectedTabText = action.tabText) }
            is HomeAction.OnPersonalRecruitmentReload -> getPersonalRecruitmentList(1, 50, "createdAt", OrderDescType.DESC.type)
            is HomeAction.OnPartyTypeSheetOpen -> _state.update { it.copy(isPartyTypeSheetOpen = action.isVisibleModal) }

            // 클릭시 이미 저장되있으면 삭제하고 없으면 추가한다.
            is HomeAction.OnSelectedPartyType -> {
                _state.update { state ->
                    val updatedList = state.selectedPartyTypeListParty.toMutableList().apply {
                        if(action.partyType == "전체"){
                            clear()
                            add("전체")
                        }else {
                            remove("전체")
                            if (contains(action.partyType)) remove(action.partyType) else add(
                                action.partyType
                            )
                        }
                    }
                    state.copy(selectedPartyTypeListParty = updatedList)
                }
            }
            is HomeAction.OnSelectedPartyTypeReset -> _state.update { it.copy(selectedPartyTypeListParty = emptyList()) }
            is HomeAction.OnPartyTypeApply -> {
                _state.update {
                    it.copy(
                        isPartyTypeSheetOpen = false,
                        isShowNumber = it.selectedPartyTypeListParty.contains("전체"),
                        number = it.selectedPartyTypeListParty.size
                    )
                }

                val selectedPartyTypeList = _state.value.selectedPartyTypeListParty
                getPartyList(
                    page = 1,
                    size = 50,
                    sort = "createdAt",
                    order = OrderDescType.DESC.type,
                    partyTypes = selectedPartyTypeList.mapNotNull { type ->
                        PartyType.entries.find { it.type == type }?.id
                    },
                    titleSearch = null,
                    status = if(_state.value.isActivePartyToggle) "active" else "archived"
                )
            }
            is HomeAction.OnActivePartyToggle -> {
                _state.update { it.copy(isActivePartyToggle = action.isActive) }
                getPartyList(
                    page = 1,
                    size = 50,
                    sort = "createdAt",
                    order = OrderDescType.DESC.type,
                    partyTypes = _state.value.selectedPartyTypeListParty.mapNotNull { type ->
                        PartyType.entries.find { it.type == type }?.id
                    },
                    titleSearch = null,
                    status = if(_state.value.isActivePartyToggle) "active" else "archived"
                )
            }
            is HomeAction.OnDescPartyArea -> {
                _state.update { currentState ->
                    val sortedList = if(action.isDesc){
                        currentState.partyList.parties.sortedByDescending { it.createdAt }
                    } else {
                        currentState.partyList.parties.sortedBy { it.createdAt }
                    }
                    currentState.copy(
                        isDescPartyArea = action.isDesc,
                        partyList = currentState.partyList.copy(parties = sortedList)
                    )
                }
            }
            is HomeAction.OnPositionSheetOpen -> _state.update { it.copy(isPositionSheetOpen = action.isVisibleModal) }
            is HomeAction.OnPartyTypeSheetOpenRecruitment -> _state.update { it.copy(isPartyTypeSheetOpenRecruitment = action.isVisibleModal) }
            is HomeAction.OnDescRecruitment -> {
                _state.update { currentState ->
                    val sortedList = if(action.isDesc){
                        currentState.recruitmentList.partyRecruitments.sortedByDescending { it.createdAt }
                    } else {
                        currentState.recruitmentList.partyRecruitments.sortedBy { it.createdAt }
                    }
                    currentState.copy(
                        isDescRecruitment = action.isDesc,
                        recruitmentList = currentState.recruitmentList.copy(partyRecruitments = sortedList)
                    )
                }
            }

            is HomeAction.OnMainPositionClick -> {
                _state.update { it.copy(selectedMainPosition = action.mainPosition) }
                getSubPositionList(action.mainPosition)
            }
            is HomeAction.OnSubPositionClick -> {
                _state.update { currentState ->
                    val updatedSubPositionList = currentState.selectedSubPositionList.toMutableList().apply {
                        if (any { it.sub == action.subPosition }) {
                            // 이미 선택된 서브 포지션을 제거
                            removeIf { it.sub == action.subPosition }
                        } else {
                            // 새로운 서브 포지션을 추가
                            currentState.getSubPositionList.find { it.sub == action.subPosition }?.let { add(it) }
                        }
                    }

                    val updatedMainAndSubPosition = currentState.selectedMainAndSubPosition.toMutableList().apply {
                        // 서브 포지션 클릭으로 업데이트된 조합을 반영
                        removeIf { pair -> pair.first == currentState.selectedMainPosition && pair.second == action.subPosition } // 중복 제거
                        updatedSubPositionList.find { it.sub == action.subPosition }?.let {
                            add(Pair(currentState.selectedMainPosition, it.sub))
                        }
                    }

                    currentState.copy(
                        selectedSubPositionList = updatedSubPositionList,
                        selectedMainAndSubPosition = updatedMainAndSubPosition
                    )
                }
            }
            is HomeAction.OnDelete -> {
                _state.update { currentState ->
                    val updatedSubPositionList = currentState.selectedSubPositionList.toMutableList().apply {
                        removeIf { it.sub == action.position.second }
                    }

                    val updatedMainAndSubPosition = currentState.selectedMainAndSubPosition.toMutableList().apply {
                        removeIf { pair -> pair.first == action.position.first && pair.second == action.position.second }
                    }

                    currentState.copy(
                        selectedSubPositionList = updatedSubPositionList,
                        selectedMainAndSubPosition = updatedMainAndSubPosition
                    )
                }
            }
            is HomeAction.OnPositionApply -> {
                _state.update { it.copy(isPositionSheetOpen = false) }
                val matchingIds = _state.value.selectedSubPositionList.filter { position ->
                    _state.value.selectedMainAndSubPosition.any { it.second == position.sub }
                }.map { it.id }

                getRecruitmentList(
                    page = 1,
                    size = 50,
                    sort = "createdAt",
                    order = OrderDescType.DESC.type,
                    titleSearch = null,
                    partyTypes = _state.value.selectedPartyTypeListParty.mapNotNull { type ->
                        PartyType.entries.find { it.type == type }?.id
                    },
                    position = matchingIds
                )
            }
            is HomeAction.OnPositionSheetReset -> {
                _state.update {
                    it.copy(
                        selectedMainPosition = "전체",
                        selectedSubPositionList = emptyList(),
                        selectedMainAndSubPosition = emptyList()
                    )
                }
            }
            is HomeAction.OnSelectedPartyTypeResetRecruitmentReset -> _state.update { it.copy(selectedPartyTypeListRecruitment = emptyList())}
            is HomeAction.OnSelectedPartyTypeRecruitment -> {
                _state.update { state ->
                    val updatedList = state.selectedPartyTypeListRecruitment.toMutableList().apply {
                        if(action.partyType == "전체"){
                            clear()
                            add("전체")
                        }else {
                            remove("전체")
                            if (contains(action.partyType)) remove(action.partyType) else add(
                                action.partyType
                            )
                        }
                    }
                    state.copy(selectedPartyTypeListRecruitment = updatedList)
                }
            }
            is HomeAction.OnPartyTypeApplyRecruitment -> {
                _state.update {
                    it.copy(
                        isPartyTypeSheetOpenRecruitment = false,
                        recruitmentIsShowNumber = it.selectedPartyTypeListRecruitment.contains("전체"),
                        recruitmentNumber = it.selectedPartyTypeListRecruitment.size
                    )
                }

                val selectedPartyTypeList = _state.value.selectedPartyTypeListRecruitment
                getRecruitmentList(
                    page = 1,
                    size = 50,
                    sort = "createdAt",
                    order = OrderDescType.DESC.type,
                    titleSearch = null,
                    partyTypes = selectedPartyTypeList.mapNotNull { type ->
                        PartyType.entries.find { it.type == type }?.id
                    }
                )
            }
            is HomeAction.OnExpandedFloating -> _state.update { it.copy(isExpandedFloating = action.isExpandedFloating) }
        }
    }

    fun scrollToTop(){
        viewModelScope.launch(Dispatchers.Main) {
            when (_state.value.selectedTabText) {
                homeTopTabList[1] -> _scrollToUpParty.emit(Unit)
                homeTopTabList[2] -> _scrollToUpRecruitment.emit(Unit)
            }
        }
    }
}