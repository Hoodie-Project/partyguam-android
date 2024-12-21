package com.party.presentation.screen.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.party.common.ServerApiResponse
import com.party.common.UIState
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
import com.party.presentation.screen.home.HomeAction
import com.party.presentation.screen.home.HomeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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

    private val _getRecruitmentListState = MutableStateFlow<UIState<ServerApiResponse<RecruitmentList>>>(UIState.Idle)
    val getRecruitmentListState: StateFlow<UIState<ServerApiResponse<RecruitmentList>>> = _getRecruitmentListState

    private val _getPartyListState = MutableStateFlow<UIState<ServerApiResponse<PartyList>>>(UIState.Idle)
    val getPartyListState: StateFlow<UIState<ServerApiResponse<PartyList>>> = _getPartyListState

    private val _positionsState = MutableStateFlow<UIState<ServerApiResponse<List<PositionList>>>>(UIState.Idle)
    val positionsState: StateFlow<UIState<ServerApiResponse<List<PositionList>>>> = _positionsState

    init {
        getBannerList()
        getPersonalRecruitmentList(1, 50, "createdAt", "DESC")
        getRecruitmentList(page = 1, size = 50, sort = "createdAt", order = "DESC", titleSearch = null)
        getPartyList(page = 1, size = 50, sort = "createdAt", order = "DESC", titleSearch = null, status = null)
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

    fun getSubPositionList(
        main: String,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            _positionsState.value = UIState.Loading
            when (val result = getPositionsUseCase(main = main)) {
                is ServerApiResponse.SuccessResponse<List<PositionList>> -> {
                    _positionsState.value = UIState.Success(result)
                }

                is ServerApiResponse.ErrorResponse<List<PositionList>> -> {
                    _positionsState.value = UIState.Idle
                }

                is ServerApiResponse.ExceptionResponse -> {
                    _positionsState.value = UIState.Idle
                }
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
            is HomeAction.OnPersonalRecruitmentReload -> getPersonalRecruitmentList(1, 50, "createdAt", "DESC")
        }
    }
}