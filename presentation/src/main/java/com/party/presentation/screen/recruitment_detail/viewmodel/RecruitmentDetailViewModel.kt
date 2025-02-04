package com.party.presentation.screen.recruitment_detail.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.party.common.ServerApiResponse
import com.party.common.UIState
import com.party.domain.model.party.CheckUserApplicationStatus
import com.party.domain.model.party.RecruitmentDetail
import com.party.domain.model.party.RecruitmentDetailParty
import com.party.domain.model.party.RecruitmentDetailPartyType
import com.party.domain.model.party.RecruitmentDetailPosition
import com.party.domain.model.user.PartyAuthority
import com.party.domain.model.user.PartyAuthorityPosition
import com.party.domain.usecase.party.CheckUserApplicationStatusUseCase
import com.party.domain.usecase.party.GetPartyAuthorityUseCase
import com.party.domain.usecase.party.GetRecruitmentDetailUseCase
import com.party.presentation.screen.recruitment_detail.RecruitmentDetailState
import com.skydoves.sandwich.StatusCode
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecruitmentDetailViewModel @Inject constructor(
    private val getRecruitmentDetailUseCase: GetRecruitmentDetailUseCase,
    private val checkUserApplicationStatusUseCase: CheckUserApplicationStatusUseCase,
    private val getPartyAuthorityUseCase: GetPartyAuthorityUseCase,
) : ViewModel() {

    private val _recruitmentDetailState = MutableStateFlow(RecruitmentDetailState())
    val recruitmentDetailState = _recruitmentDetailState.asStateFlow()

    fun checkUserApplicationStatus(partyId: Int, partyRecruitmentId: Int){
        viewModelScope.launch(Dispatchers.IO) {
            when(val result = checkUserApplicationStatusUseCase(partyId = partyId, partyRecruitmentId = partyRecruitmentId)){
                is ServerApiResponse.SuccessResponse -> {
                    _recruitmentDetailState.update { it.copy(isRecruitment = true) } // 이미 지원한 경우
                }
                is ServerApiResponse.ErrorResponse -> {
                    when(result.statusCode){
                        StatusCode.NotFound.code -> {
                            _recruitmentDetailState.update { it.copy(isRecruitment = false) } // 지원하지 않은 경우
                        }
                        StatusCode.Forbidden.code -> {} // 데이터가 잘못된 경우
                    }
                }
                is ServerApiResponse.ExceptionResponse -> {}
            }
        }
    }

    fun getRecruitmentDetail(partyRecruitmentId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _recruitmentDetailState.update { it.copy(isLoading = true) }

            when (val result = getRecruitmentDetailUseCase(partyRecruitmentId = partyRecruitmentId)) {
                is ServerApiResponse.SuccessResponse<RecruitmentDetail> -> {
                    _recruitmentDetailState.update {
                        it.copy(
                            isLoading = false,
                            recruitmentDetail = result.data ?: RecruitmentDetail(
                                party = RecruitmentDetailParty(
                                    title = "",
                                    image = "",
                                    status = "",
                                    partyType = RecruitmentDetailPartyType(type = "")
                                ),
                                position = RecruitmentDetailPosition(
                                    id = 0,
                                    main = "",
                                    sub = ""
                                ),
                                content = "",
                                recruitingCount = 0,
                                recruitedCount = 0,
                                applicationCount = 0,
                                createdAt = "2024-06-05T15:30:45.123Z"
                            )
                        )
                    }
                }

                is ServerApiResponse.ErrorResponse<RecruitmentDetail> -> { _recruitmentDetailState.update { it.copy(isLoading = false) } }
                is ServerApiResponse.ExceptionResponse -> { _recruitmentDetailState.update { it.copy(isLoading = false) } }
            }
        }
    }

    fun getPartyAuthority(partyId: Int){
        viewModelScope.launch(Dispatchers.IO) {
            when(val result = getPartyAuthorityUseCase(partyId = partyId)){
                is ServerApiResponse.SuccessResponse -> {
                    _recruitmentDetailState.update { it.copy(partyAuthority = result.data ?: PartyAuthority(id = 0, authority = "", position = PartyAuthorityPosition(0, "", ""))) }
                }
                is ServerApiResponse.ErrorResponse -> {}
                is ServerApiResponse.ExceptionResponse -> {}
            }
        }
    }
}