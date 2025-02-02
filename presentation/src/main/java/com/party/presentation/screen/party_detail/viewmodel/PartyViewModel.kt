package com.party.presentation.screen.party_detail.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.party.common.ServerApiResponse
import com.party.domain.model.party.PartyDetail
import com.party.domain.model.party.PartyType
import com.party.domain.model.party.PartyUsers
import com.party.domain.model.user.PartyAuthority
import com.party.domain.model.user.PartyAuthorityPosition
import com.party.domain.usecase.party.GetPartyAuthorityUseCase
import com.party.domain.usecase.party.GetPartyDetailUseCase
import com.party.domain.usecase.party.GetPartyRecruitmentUseCase
import com.party.domain.usecase.party.GetPartyUsersUseCase
import com.party.presentation.enum.OrderDescType
import com.party.presentation.screen.party_detail.PartyDetailAction
import com.party.presentation.screen.party_detail.PartyDetailState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PartyViewModel @Inject constructor(
    private val getPartyDetailUseCase: GetPartyDetailUseCase,
    private val getPartyRecruitmentUseCase: GetPartyRecruitmentUseCase,
    private val getPartyUsersUseCase: GetPartyUsersUseCase,
    private val getPartyAuthorityUseCase: GetPartyAuthorityUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(PartyDetailState())
    val state = _state.asStateFlow()

    fun getPartyDetail(partyId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(isLoadingPartyDetail = true) }
            when (val result = getPartyDetailUseCase(partyId = partyId)) {
                is ServerApiResponse.SuccessResponse -> {
                    _state.update { it.copy(
                        isLoadingPartyDetail = false,
                        partyDetail = result.data ?: PartyDetail(
                            id = 0,
                            partyType = PartyType(id = 0, type = ""),
                            title = "",
                            content = "",
                            image = null,
                            status = "",
                            createdAt = "2024-06-05T15:30:45.123Z",
                            updatedAt = "2024-06-05T15:30:45.123Z"
                        )
                    ) }
                }

                is ServerApiResponse.ErrorResponse -> {
                    _state.update { it.copy(isLoadingPartyDetail = false) }
                }

                is ServerApiResponse.ExceptionResponse -> {
                    _state.update { it.copy(isLoadingPartyDetail = false) }
                }
            }
        }
    }

    fun getPartyUsers(partyId: Int, page: Int, limit: Int, sort: String, order: String){
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(isLoadingPartyUser = true) }
            when(val result = getPartyUsersUseCase(partyId = partyId, page = page, limit = limit, sort = sort, order = order)){
                is ServerApiResponse.SuccessResponse -> {
                    _state.update { it.copy(
                        isLoadingPartyUser = false,
                        partyUser = result.data ?: PartyUsers(
                            partyAdmin = emptyList(),
                            partyUser = emptyList()
                        )
                    ) }
                }
                is ServerApiResponse.ErrorResponse -> { _state.update { it.copy(isLoadingPartyUser = false) } }
                is ServerApiResponse.ExceptionResponse -> { _state.update { it.copy(isLoadingPartyUser = false) } }
            }
        }
    }

    fun getPartyRecruitment(partyId: Int, sort: String, order: String, main: String?, status: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(isLoadingPartyRecruitment = true) }
            when (val result = getPartyRecruitmentUseCase(partyId = partyId, sort = sort, order = order, main = main, status = status)) {
                is ServerApiResponse.SuccessResponse -> {
                    _state.update { it.copy(
                        isLoadingPartyRecruitment = false,
                        partyRecruitment = result.data ?: emptyList()
                    ) }
                }
                is ServerApiResponse.ErrorResponse -> { _state.update { it.copy(isLoadingPartyRecruitment = false) } }
                is ServerApiResponse.ExceptionResponse -> { _state.update { it.copy(isLoadingPartyRecruitment = false) } }
            }
        }
    }

    fun getPartyAuthority(partyId: Int){
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(isLoadingPartyAuthority = true) }
            when(val result = getPartyAuthorityUseCase(partyId = partyId)){
                is ServerApiResponse.SuccessResponse -> {
                    _state.update { it.copy(
                        isLoadingPartyAuthority = false,
                        partyAuthority = result.data ?: PartyAuthority(id = 0, authority = "", position = PartyAuthorityPosition(0, "", "")))
                    }
                }
                is ServerApiResponse.ErrorResponse -> _state.update { it.copy(isLoadingPartyAuthority = false) }
                is ServerApiResponse.ExceptionResponse -> _state.update { it.copy(isLoadingPartyAuthority = false) }
            }
        }
    }

    fun onAction(action: PartyDetailAction) {
        when(action){
            is PartyDetailAction.OnTabClick -> _state.update { it.copy(selectedTabText = action.tabText) }
            is PartyDetailAction.OnShowPositionFilter -> _state.update { it.copy(isShowPositionFilter = action.isShow) }
            is PartyDetailAction.OnPositionClick -> _state.update { it.copy(selectedPosition = action.position) }
            is PartyDetailAction.OnReset -> _state.update { it.copy(selectedPosition = "") }
            is PartyDetailAction.OnApply -> {
                _state.update { currentState ->
                    currentState.copy(
                        selectedPosition = currentState.selectedPosition,
                        isShowPositionFilter = false
                    )
                }
                getPartyRecruitment(
                    partyId = action.partyId,
                    sort = "createdAt",
                    order = OrderDescType.DESC.type,
                    main = if (_state.value.selectedPosition == "전체") null else _state.value.selectedPosition,
                    status = if(_state.value.isProgress) "active" else "completed"
                )
            }
            is PartyDetailAction.OnChangeOrderBy -> {
                _state.update { currentState ->
                    val sortedList = if (action.selectedOrderBy) {
                        currentState.partyRecruitment.sortedByDescending { it.createdAt }
                    } else {
                        currentState.partyRecruitment.sortedBy { it.createdAt }
                    }
                    currentState.copy(
                        selectedOrderBy = action.selectedOrderBy,
                        partyRecruitment = sortedList
                    )
                }
            }
            is PartyDetailAction.OnChangeProgress -> {
                _state.update { it.copy(isProgress = action.isProgress) }

                getPartyRecruitment(
                    partyId = action.partyId,
                    sort = "createdAt",
                    order = OrderDescType.DESC.type,
                    main = if (_state.value.selectedPosition == "전체") null else _state.value.selectedPosition,
                    status = if(_state.value.isProgress) "active" else "completed"
                )
            }
            is PartyDetailAction.OnShowMoreBottomSheet -> { _state.update { it.copy(isShowMoreBottomSheet = action.isShow) }}
        }
    }
}