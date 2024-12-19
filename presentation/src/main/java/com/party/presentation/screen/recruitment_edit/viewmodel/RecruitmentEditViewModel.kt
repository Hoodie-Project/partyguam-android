package com.party.presentation.screen.recruitment_edit.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.party.common.ServerApiResponse
import com.party.domain.model.party.ModifyRecruitmentRequest
import com.party.domain.model.party.RecruitmentDetail
import com.party.domain.model.party.RecruitmentDetailParty
import com.party.domain.model.party.RecruitmentDetailPartyType
import com.party.domain.model.party.RecruitmentDetailPosition
import com.party.domain.model.user.detail.PositionList
import com.party.domain.usecase.party.GetRecruitmentDetailUseCase
import com.party.domain.usecase.party.ModifyRecruitmentUseCase
import com.party.domain.usecase.user.detail.GetPositionsUseCase
import com.party.presentation.screen.recruitment_edit.RecruitmentEditAction
import com.party.presentation.screen.recruitment_edit.RecruitmentEditState
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
class RecruitmentEditViewModel @Inject constructor(
    private val getPositionsUseCase: GetPositionsUseCase,
    private val getRecruitmentDetailUseCase: GetRecruitmentDetailUseCase,
    private val modifyRecruitmentUseCase: ModifyRecruitmentUseCase,
): ViewModel(){

    private val _state = MutableStateFlow(RecruitmentEditState())
    val state = _state.asStateFlow()

    private val _successModify = MutableSharedFlow<Unit>()
    val successModify = _successModify.asSharedFlow()

    private fun modifyRecruitment(partyId: Int, partyRecruitmentId: Int, modifyRecruitmentRequest: ModifyRecruitmentRequest){
        viewModelScope.launch(Dispatchers.IO) {
            when(val result = modifyRecruitmentUseCase(partyId = partyId, partyRecruitmentId = partyRecruitmentId, modifyRecruitmentRequest = modifyRecruitmentRequest)){
                is ServerApiResponse.SuccessResponse -> _successModify.emit(Unit)
                is ServerApiResponse.ErrorResponse -> {}
                is ServerApiResponse.ExceptionResponse -> {}
            }
        }
    }

    fun getRecruitmentDetail(partyRecruitmentId: Int){
        viewModelScope.launch(Dispatchers.IO) {
            when(val result = getRecruitmentDetailUseCase(partyRecruitmentId = partyRecruitmentId)){
                is ServerApiResponse.SuccessResponse<RecruitmentDetail> -> {
                    val recruitmentDetail = result.data ?: RecruitmentDetail(
                        party = RecruitmentDetailParty(
                            title = "",
                            image = "",
                            status = "",
                            partyType = RecruitmentDetailPartyType(type = "")
                        ),
                        position = RecruitmentDetailPosition(
                            main = "",
                            sub = ""
                        ),
                        content = "",
                        recruitingCount = 0,
                        recruitedCount = 0,
                        applicationCount = 0,
                        createdAt = "2024-06-05T15:30:45.123Z"
                    )

                    _state.update {
                        it.copy(
                            selectedMainPosition = recruitmentDetail.position.main,
                            selectedSubPosition = PositionList(0, recruitmentDetail.position.main, recruitmentDetail.position.sub),
                            selectedCount = "${recruitmentDetail.recruitingCount}명",
                            recruitmentDescription = recruitmentDetail.content,
                        )
                    }
                }
                is ServerApiResponse.ErrorResponse<RecruitmentDetail> -> {}
                is ServerApiResponse.ExceptionResponse -> {}
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

    fun onAction(action: RecruitmentEditAction){
        when(action){
            is RecruitmentEditAction.OnChangeMainPositionBottomSheet -> _state.update { it.copy(isMainPositionBottomSheetShow = action.isMainPositionBottomSheetShow) }
            is RecruitmentEditAction.OnChangeMainPosition -> _state.update { it.copy(selectedMainPosition = action.position) }
            is RecruitmentEditAction.OnChangeSubPosition -> _state.update { it.copy(selectedSubPosition = action.positionList) }
            is RecruitmentEditAction.OnSetSelectedCount -> _state.update { it.copy(selectedCount = action.selectedCount) }
            is RecruitmentEditAction.OnChangePeopleCountSheet -> _state.update { it.copy(isPeopleCountSheetOpen = action.isPeopleCountSheetOpen) }
            is RecruitmentEditAction.OnChangeHelpCardOpen -> _state.update { it.copy(isHelpCardOpen = action.isHelpCardOpen) }
            is RecruitmentEditAction.OnChangeRecruitmentDescription -> _state.update { it.copy(recruitmentDescription = action.recruitmentDescription) }
            is RecruitmentEditAction.OnModifyRecruitment -> {
                val modifyRecruitmentRequest = ModifyRecruitmentRequest(
                    positionId = _state.value.selectedSubPosition.id,
                    content = _state.value.recruitmentDescription,
                    recruiting_count = _state.value.selectedCount.split("명")[0].toInt(),
                )
                modifyRecruitment(action.partyId, action.partyRecruitmentId, modifyRecruitmentRequest)
            }
        }
    }
}