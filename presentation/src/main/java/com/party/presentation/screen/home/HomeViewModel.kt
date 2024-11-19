package com.party.presentation.screen.home

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.party.common.DetailCarrierData.mainSelectedMainPosition
import com.party.common.ServerApiResponse
import com.party.common.UIState
import com.party.domain.model.banner.Banner
import com.party.domain.model.party.PartyListResponse
import com.party.domain.model.party.PersonalRecruitmentListResponse
import com.party.domain.model.party.RecruitmentItemResponse
import com.party.domain.model.party.RecruitmentListResponse
import com.party.domain.model.user.detail.PositionListResponse
import com.party.domain.usecase.banner.GetBannerListUseCase
import com.party.domain.usecase.party.GetPartyListUseCase
import com.party.domain.usecase.party.GetPersonalRecruitmentListUseCase
import com.party.domain.usecase.party.GetRecruitmentListUseCase
import com.party.domain.usecase.user.detail.GetPositionsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filter
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

    private val _getPersonalRecruitmentListState = MutableStateFlow<UIState<ServerApiResponse<PersonalRecruitmentListResponse>>>(UIState.Idle)
    val getPersonalRecruitmentListState: StateFlow<UIState<ServerApiResponse<PersonalRecruitmentListResponse>>> = _getPersonalRecruitmentListState

    private val _getRecruitmentListState =
        MutableStateFlow<UIState<ServerApiResponse<RecruitmentListResponse>>>(UIState.Idle)
    val getRecruitmentListState: StateFlow<UIState<ServerApiResponse<RecruitmentListResponse>>> = _getRecruitmentListState

    private val _getPartyListState = MutableStateFlow<UIState<ServerApiResponse<PartyListResponse>>>(UIState.Idle)
    val getPartyListState: StateFlow<UIState<ServerApiResponse<PartyListResponse>>> = _getPartyListState

    private val _positionsState = MutableStateFlow<UIState<ServerApiResponse<List<PositionListResponse>>>>(UIState.Idle)
    val positionsState: StateFlow<UIState<ServerApiResponse<List<PositionListResponse>>>> = _positionsState

    private val _bannerListState = MutableStateFlow<UIState<ServerApiResponse<Banner>>>(UIState.Idle)
    val bannerListState: StateFlow<UIState<ServerApiResponse<Banner>>> = _bannerListState

    init {
        getBannerList()
    }

    fun getPersonalRecruitmentList(
        page: Int,
        size: Int,
        sort: String,
        order: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            _getPersonalRecruitmentListState.value = UIState.Loading
            when (val result = getPersonalRecruitmentListUseCase(
                page = page,
                size = size,
                sort = sort,
                order = order
            )) {
                is ServerApiResponse.SuccessResponse<PersonalRecruitmentListResponse> -> {
                    _getPersonalRecruitmentListState.value = UIState.Success(result)
                }

                is ServerApiResponse.ErrorResponse<PersonalRecruitmentListResponse> -> {
                    _getPersonalRecruitmentListState.value = UIState.Error()
                }

                is ServerApiResponse.ExceptionResponse -> {
                    _getPersonalRecruitmentListState.value = UIState.Idle
                }
            }
        }
    }

    fun getRecruitmentList(
        page: Int,
        size: Int,
        sort: String,
        order: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            _getRecruitmentListState.value = UIState.Loading
            when (val result =
                getRecruitmentListUseCase(page = page, size = size, sort = sort, order = order)) {
                is ServerApiResponse.SuccessResponse<RecruitmentListResponse> -> {
                    _getRecruitmentListState.value = UIState.Success(result)
                }

                is ServerApiResponse.ErrorResponse<RecruitmentListResponse> -> {
                    _getRecruitmentListState.value = UIState.Idle
                }

                is ServerApiResponse.ExceptionResponse -> {
                    _getRecruitmentListState.value = UIState.Idle
                }
            }
        }
    }

    fun getPartyList(
        page: Int,
        size: Int,
        sort: String,
        order: String,
        partyTypes: List<Int> = emptyList()
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            _getPartyListState.value = UIState.Loading
            when (val result = getPartyListUseCase(
                page = page,
                size = size,
                sort = sort,
                order = order,
                partyTypes = partyTypes
            )) {
                is ServerApiResponse.SuccessResponse<PartyListResponse> -> {
                    _getPartyListState.value = UIState.Success(result)
                }

                is ServerApiResponse.ErrorResponse<PartyListResponse> -> {
                    _getPartyListState.value = UIState.Idle
                }

                is ServerApiResponse.ExceptionResponse -> {
                    _getPartyListState.value = UIState.Idle
                }
            }
        }
    }

    fun getSubPositionList(
        main: String,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            _positionsState.value = UIState.Loading
            when (val result = getPositionsUseCase(main = main)) {
                is ServerApiResponse.SuccessResponse<List<PositionListResponse>> -> {
                    _positionsState.value = UIState.Success(result)
                }

                is ServerApiResponse.ErrorResponse<List<PositionListResponse>> -> {
                    _positionsState.value = UIState.Idle
                }

                is ServerApiResponse.ExceptionResponse -> {
                    _positionsState.value = UIState.Idle
                }
            }
        }
    }

    fun getBannerList() {
        viewModelScope.launch(Dispatchers.IO) {
            _bannerListState.value = UIState.Loading
            when (val result = getBannerListUseCase()) {
                is ServerApiResponse.SuccessResponse<Banner> -> {
                    _bannerListState.value = UIState.Success(result)
                }

                is ServerApiResponse.ErrorResponse<Banner> -> {
                    _bannerListState.value = UIState.Error()
                }

                is ServerApiResponse.ExceptionResponse -> {
                    _bannerListState.value = UIState.Exception
                }
            }
        }
    }
}