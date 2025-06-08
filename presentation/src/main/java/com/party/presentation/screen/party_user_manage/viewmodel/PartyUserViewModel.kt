package com.party.presentation.screen.party_user_manage.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.party.common.ServerApiResponse
import com.party.common.component.bottomsheet.mainPositionList
import com.party.domain.model.party.DelegatePartyMasterRequest
import com.party.domain.model.party.ModifyPartyUserPositionRequest
import com.party.domain.usecase.party.DelegatePartyMasterUseCase
import com.party.domain.usecase.party.DeletePartyMemberUseCase
import com.party.domain.usecase.party.GetPartyMemberInfoUseCase
import com.party.domain.usecase.party.ModifyPartyUserPositionUseCase
import com.party.domain.usecase.user.detail.GetPositionsUseCase
import com.party.presentation.enum.OrderDescType
import com.party.presentation.screen.party_user_manage.PartyUserAction
import com.party.presentation.screen.party_user_manage.PartyUserState
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
class PartyUserViewModel @Inject constructor(
    private val getPartyMemberInfoUseCase: GetPartyMemberInfoUseCase,
    private val getPositionsUseCase: GetPositionsUseCase,
    private val modifyPartyUserPositionUseCase: ModifyPartyUserPositionUseCase,
    private val deletePartyMemberUseCase: DeletePartyMemberUseCase,
    private val delegatePartyMasterUseCase: DelegatePartyMasterUseCase,
): ViewModel(){

    private val _errorFlow = MutableSharedFlow<String>()
    val errorFlow = _errorFlow.asSharedFlow()

    // 포지션 변경 성공
    private val _modifySuccessFlow = MutableSharedFlow<Unit>()
    val modifySuccessFlow = _modifySuccessFlow.asSharedFlow()

    // 파티원 내보내기 성공
    private val _deleteSuccessFlow = MutableSharedFlow<Unit>()
    val deleteSuccessFlow = _deleteSuccessFlow.asSharedFlow()

    // 파티장 위임하기 성공
    private val _delegateMasterSuccessFlow = MutableSharedFlow<Unit>()
    val delegateMasterSuccessFlow = _delegateMasterSuccessFlow.asSharedFlow()

    private val _state = MutableStateFlow(PartyUserState())
    val state = _state.asStateFlow()

    init {
        getSubPositionList(main = mainPositionList[1])
    }

    fun getPartyMembers(
        partyId: Int,
        page: Int,
        limit: Int,
        sort: String,
        order: String,
        main: String?,
        nickname: String?
    ){
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            when(val result = getPartyMemberInfoUseCase(
                partyId = partyId,
                page = page,
                limit = limit,
                sort = sort,
                order = order,
                main = main,
                nickname = nickname
            )){
                is ServerApiResponse.SuccessResponse -> {
                    _state.update { it.copy(
                        isLoading = false,
                        partyUserList = result.data?.partyUser ?: emptyList(),
                        filteredPartyUserList = result.data?.partyUser ?: emptyList()
                    ) }
                }
                is ServerApiResponse.ErrorResponse -> _state.update { it.copy(isLoading = false) }
                is ServerApiResponse.ExceptionResponse -> _state.update { it.copy(isLoading = false) }
            }
        }
    }

    fun dismissBackDialog(){
        _state.update { it.copy(isShowModifyDialog = false) }
    }

    fun dismissChangeMasterDialog(){
        _state.update { it.copy(isShowChangeMaster = false) }
    }

    // 서브 포지션 조회
    private fun getSubPositionList(
        main: String,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = getPositionsUseCase(main = main)) {
                is ServerApiResponse.SuccessResponse -> { _state.update { it.copy(getSubPositionList = result.data ?: emptyList()) } }
                is ServerApiResponse.ErrorResponse -> {}
                is ServerApiResponse.ExceptionResponse -> {}
            }
        }
    }

    private fun modifyPartyUserPosition(
        partyId: Int,
        partyUserId: Int,
        modifyPartyUserPositionRequest: ModifyPartyUserPositionRequest,
    ){
        viewModelScope.launch(Dispatchers.IO) {
            when(val result = modifyPartyUserPositionUseCase(partyId, partyUserId, modifyPartyUserPositionRequest)){
                is ServerApiResponse.SuccessResponse -> {
                    _modifySuccessFlow.emit(Unit)
                    getPartyMembers(
                        partyId = partyId,
                        page = 1,
                        limit = 10,
                        sort = "createdAt",
                        order = OrderDescType.DESC.type,
                        main = null,
                        nickname = null
                    )
                }
                is ServerApiResponse.ErrorResponse -> {
                    _state.update { it.copy(isShowModifyDialog = false) }
                    when(result.statusCode){
                        404 -> _errorFlow.emit("파티유저를 찾을 수 없습니다.")
                        403 -> _errorFlow.emit("파티원 수정권한이 없습니다.")
                        else -> _errorFlow.emit("파티원 수정에 실패했습니다.")
                    }
                }
                is ServerApiResponse.ExceptionResponse -> _state.update { it.copy(isShowModifyDialog = false) }
            }
        }
    }

    private fun deletePartyMember(partyId: Int, partyUserId: Int){
        viewModelScope.launch(Dispatchers.IO) {
            when(val result = deletePartyMemberUseCase(partyId, partyUserId)){
                is ServerApiResponse.SuccessResponse -> {
                    _deleteSuccessFlow.emit(Unit)
                    getPartyMembers(
                        partyId = partyId,
                        page = 1,
                        limit = 10,
                        sort = "createdAt",
                        order = OrderDescType.DESC.type,
                        main = null,
                        nickname = null
                    )
                }
                is ServerApiResponse.ErrorResponse -> {
                    when(result.statusCode){
                        404 -> _errorFlow.emit("파티유저를 찾을 수 없습니다.")
                        403 -> _errorFlow.emit("파티원 삭제권한이 없습니다.")
                        else -> _errorFlow.emit("파티원 삭제에 실패했습니다.")
                    }
                }
                is ServerApiResponse.ExceptionResponse -> {}
            }
        }
    }

    private fun changeMaster(partyId: Int, delegatePartyMasterRequest: DelegatePartyMasterRequest){
        viewModelScope.launch(Dispatchers.IO) {
            when(val result = delegatePartyMasterUseCase(partyId = partyId, delegatePartyMasterRequest = delegatePartyMasterRequest)){
                is ServerApiResponse.SuccessResponse -> {
                    _delegateMasterSuccessFlow.emit(Unit)
                }
                is ServerApiResponse.ErrorResponse -> {}
                is ServerApiResponse.ExceptionResponse -> {}
            }
        }
    }

    fun onAction(action: PartyUserAction){
        when(action){
            is PartyUserAction.OnChangeInputText -> _state.update { it.copy(inputText = action.inputText) }
            is PartyUserAction.OnChangePositionBottomSheet -> _state.update { it.copy(isOpenPositionBottomSheet = action.isOpenPositionBottomSheet) }
            is PartyUserAction.OnChangeMainPosition -> _state.update { it.copy(selectedMainPosition = action.selectedMainPosition) }
            is PartyUserAction.OnChangeOrderBy -> {
                _state.update { currentState ->
                    val sortedList = if(action.isDesc) {
                        currentState.partyUserList.sortedByDescending { it.createdAt }
                    } else {
                        currentState.partyUserList.sortedBy { it.createdAt }
                    }
                    currentState.copy(
                        filteredPartyUserList = sortedList,
                        isDesc = action.isDesc,
                    )
                }
            }
            is PartyUserAction.OnManageBottomSheet -> { _state.update { it.copy(manageBottomSheet = action.isOpenManageBottomSheet) } }
            is PartyUserAction.OnSelectedUser ->{
                _state.update { it.copy(
                    selectedMemberAuthority = action.selectedMemberAuthority,
                    selectedPartyMemberId = action.selectedMemberId,
                ) }
            }
            is PartyUserAction.OnApply -> {
                val filteredList = _state.value.partyUserList.filter {
                    action.selectedMainPosition == "전체" || it.position.main == action.selectedMainPosition
                }

                _state.update { it.copy(
                    selectedMainPosition = action.selectedMainPosition,
                    isOpenPositionBottomSheet = false,
                    filteredPartyUserList = filteredList,
                ) }
            }

            is PartyUserAction.OnMainPositionClick -> { getSubPositionList(action.mainPosition) }
            is PartyUserAction.OnChangeModifyPositionSheet -> _state.update { it.copy(isMainPositionBottomSheetShow = action.isOpenMainPositionSheet) }
            is PartyUserAction.OnChangeSelectedSubPosition -> _state.update { it.copy(selectedSubPosition = action.positionList) }
            is PartyUserAction.OnChangeModifyDialog -> _state.update { it.copy(isShowModifyDialog = action.isShowModifyDialog) }
            is PartyUserAction.OnModifyUserPosition -> {
                _state.update { it.copy(isShowModifyDialog = false) }
                modifyPartyUserPosition(
                    partyId = action.partyId,
                    partyUserId = _state.value.selectedPartyMemberId,
                    modifyPartyUserPositionRequest = ModifyPartyUserPositionRequest(
                        positionId = _state.value.selectedSubPosition.id
                    )
                )

            }
            is PartyUserAction.OnSearch -> {
                val filteredList = _state.value.partyUserList.filter {
                    it.user.nickname.contains(action.inputText)
                }
                _state.update {
                    it.copy(
                        inputText = action.inputText,
                        filteredPartyUserList = filteredList
                    )
                }
            }
            is PartyUserAction.OnDeletePartyMember -> {
                _state.update { it.copy(manageBottomSheet = false) }
                deletePartyMember(partyId = action.partyId, partyUserId = _state.value.selectedPartyMemberId)
            }
            is PartyUserAction.OnChangeMasterDialog -> _state.update { it.copy(isShowChangeMaster = action.isShowChangeMaster) }
            is PartyUserAction.OnChangeMaster -> {
                _state.update { it.copy(isShowChangeMaster = false) }
                changeMaster(action.partyId, DelegatePartyMasterRequest(_state.value.selectedPartyMemberId))
            }
        }
    }
}