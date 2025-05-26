package com.party.presentation.screen.recruitment_create.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.party.common.ServerApiResponse
import com.party.common.UIState
import com.party.common.component.bottomsheet.mainPositionList
import com.party.domain.model.party.RecruitmentCreate
import com.party.domain.model.party.RecruitmentCreateRequest
import com.party.domain.model.user.detail.PositionList
import com.party.domain.usecase.party.CreateRecruitmentUseCase
import com.party.domain.usecase.user.detail.GetPositionsUseCase
import com.party.presentation.screen.recruitment_create.RecruitmentCreateAction
import com.party.presentation.screen.recruitment_create.RecruitmentState
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
class RecruitmentCreateViewModel @Inject constructor(
    private val createRecruitmentUseCase: CreateRecruitmentUseCase,
    private val getPositionsUseCase: GetPositionsUseCase,
): ViewModel(){

    private val _state = MutableStateFlow(RecruitmentState())
    val state = _state.asStateFlow()

    private val _successSaveState = MutableSharedFlow<Unit>()
    val successSaveState = _successSaveState.asSharedFlow()

    init {
        getSubPositionList(mainPositionList[1])
    }

    private fun createRecruitment(partyId: Int, recruitmentCreateRequest: RecruitmentCreateRequest){
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(isRecruitmentCreateLoading = true) }
            when(val result = createRecruitmentUseCase(partyId = partyId, recruitmentCreateRequest = recruitmentCreateRequest)){
                is ServerApiResponse.SuccessResponse -> {
                    _state.update { it.copy(isRecruitmentCreateLoading = false) }
                    _successSaveState.emit(Unit)
                }
                is ServerApiResponse.ErrorResponse -> _state.update { it.copy(isRecruitmentCreateLoading = false) }
                is ServerApiResponse.ExceptionResponse -> _state.update { it.copy(isRecruitmentCreateLoading = false) }
            }
        }
    }

    fun getSubPositionList(
        main: String,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = getPositionsUseCase(main = main)) {
                is ServerApiResponse.SuccessResponse<List<PositionList>> -> {
                    _state.update { it.copy(
                        subPositionList = result.data ?: emptyList(),
                    ) }
                }
                is ServerApiResponse.ErrorResponse<List<PositionList>> -> {}
                is ServerApiResponse.ExceptionResponse -> {}
            }
        }
    }

    fun onAction(action: RecruitmentCreateAction) {
        when (action) {
            is RecruitmentCreateAction.OnChangeMainPositionBottomSheet -> _state.update { it.copy(isMainPositionBottomSheetShow = action.isMainPositionBottomSheetShow) }
            is RecruitmentCreateAction.OnChangeMainPosition -> _state.update { it.copy(selectedMainPosition = action.position) }
            is RecruitmentCreateAction.OnChangeSubPosition -> _state.update { it.copy(selectedSubPosition = action.positionList) }
            is RecruitmentCreateAction.OnSetSelectedCount -> _state.update { it.copy(selectedCount = action.selectedCount.toInt()) }
            is RecruitmentCreateAction.OnChangePeopleCountSheet -> _state.update { it.copy(isPeopleCountSheetOpen = action.isPeopleCountSheetOpen) }
            is RecruitmentCreateAction.OnChangeHelpCardOpen -> _state.update { it.copy(isHelpCardOpen = action.isHelpCardOpen) }
            is RecruitmentCreateAction.OnChangeRecruitmentDescription -> _state.update { it.copy(recruitmentDescription = action.recruitmentDescription) }
            is RecruitmentCreateAction.OnRecruitmentCreate -> {
                val recruitmentCreateRequest = RecruitmentCreateRequest(
                    positionId = _state.value.selectedSubPosition.id,
                    content = _state.value.recruitmentDescription,
                    recruiting_count = _state.value.selectedCount,
                )
                createRecruitment(action.partyId, recruitmentCreateRequest)
            }
        }
    }
}