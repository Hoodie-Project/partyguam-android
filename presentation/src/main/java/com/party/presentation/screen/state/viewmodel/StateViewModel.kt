package com.party.presentation.screen.state.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.party.common.ServerApiResponse
import com.party.domain.model.user.party.MyParty
import com.party.domain.usecase.user.party.GetMyPartyUseCase
import com.party.presentation.screen.state.MyPartyAction
import com.party.presentation.screen.state.MyPartyState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StateViewModel @Inject constructor(
    private val getMyPartyUseCase: GetMyPartyUseCase,
): ViewModel(){

    private val _myPartyState = MutableStateFlow(MyPartyState())
    val myPartyState = _myPartyState.asStateFlow()

    fun getMyParty(
        page: Int,
        limit: Int,
        sort: String,
        order: String,
    ){
        viewModelScope.launch(Dispatchers.IO) {
            _myPartyState.update { it.copy(isLoading = true) }

            when(val result = getMyPartyUseCase(page, limit, sort, order)){
                is ServerApiResponse.SuccessResponse -> {
                    _myPartyState.update {
                        it.copy(
                            isLoading = false,
                            myPartyList = result.data ?: MyParty(total = 0, partyUsers = emptyList())
                        )
                    }
                }
                is ServerApiResponse.ErrorResponse -> {
                    _myPartyState.update { it.copy(isLoading = false) }
                }

                is ServerApiResponse.ExceptionResponse -> {
                    _myPartyState.update { it.copy(isLoading = false) }
                }
            }
        }
    }

    fun onAction(action: MyPartyAction){
        when(action){
            is MyPartyAction.OnOrderByChange -> {
                _myPartyState.update { currentState ->
                    val sortedList = if (action.orderByDesc) {
                        currentState.myPartyList.partyUsers.sortedByDescending { it.createdAt }
                    } else {
                        currentState.myPartyList.partyUsers.sortedBy { it.createdAt }
                    }
                    currentState.copy(
                        orderByDesc = action.orderByDesc,
                        myPartyList = currentState.myPartyList.copy(partyUsers = sortedList)
                    )
                }
            }
        }
    }
}