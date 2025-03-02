package com.party.presentation.screen.profile_edit_career.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.party.common.ServerApiResponse
import com.party.domain.model.user.detail.GetCarrier
import com.party.domain.model.user.detail.GetCarrierPosition
import com.party.domain.model.user.detail.ModifyCarrierList
import com.party.domain.model.user.detail.PositionList
import com.party.domain.model.user.detail.SaveCarrierList
import com.party.domain.usecase.user.detail.DeleteUserCareerUseCase
import com.party.domain.usecase.user.detail.GetCarrierUseCase
import com.party.domain.usecase.user.detail.GetPositionsUseCase
import com.party.domain.usecase.user.detail.ModifyCarrierUseCase
import com.party.domain.usecase.user.detail.SaveCarrierUseCase
import com.party.presentation.screen.profile_edit_career.ProfileEditCareerAction
import com.party.presentation.screen.profile_edit_career.ProfileEditCareerState
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
class ProfileEditCareerViewModel @Inject constructor(
    private val getPositionsUseCase: GetPositionsUseCase,
    private val saveCarrierUseCase: SaveCarrierUseCase,
    private val deleteUserCareerUseCase: DeleteUserCareerUseCase,
    private val modifyCarrierUseCase: ModifyCarrierUseCase,
    private val getCarrierUseCase: GetCarrierUseCase,
): ViewModel(){

    private val _state = MutableStateFlow(ProfileEditCareerState())
    val state = _state.asStateFlow()

    private val _saveSuccessState = MutableSharedFlow<Unit>()
    val saveSuccessState = _saveSuccessState.asSharedFlow()

    init {
        getCarrier()
    }

    fun navigateScreen(){
        _state.update { it.copy(isShowPrevScreen = true) }
    }

    private fun getCarrier(){
        viewModelScope.launch(Dispatchers.IO) {
            when(val result = getCarrierUseCase()){
                is ServerApiResponse.SuccessResponse -> {
                    result.data?.forEach {
                        if(it.careerType == "primary"){
                            _state.update { state ->
                                state.copy(getMainPosition = it)
                            }
                        }else{
                            _state.update { state ->
                                state.copy(getSubPosition = it)
                            }
                        }
                    }
                }
                is ServerApiResponse.ErrorResponse -> {}
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

    fun deleteCareer(career: SaveCarrierList){
        viewModelScope.launch(Dispatchers.IO) {
            when(val result = deleteUserCareerUseCase()){
                is ServerApiResponse.SuccessResponse -> {
                    if( !(_state.value.getMainPosition == null && _state.value.getSubPosition == null)){
                        saveCarrier(career = career)
                    }else {
                        _saveSuccessState.emit(Unit)
                    }
                }
                is ServerApiResponse.ErrorResponse -> {}
                is ServerApiResponse.ExceptionResponse -> {}
            }
        }
    }

    private fun modifyCarrier(career: ModifyCarrierList){
        viewModelScope.launch(Dispatchers.IO) {
            when(val result = modifyCarrierUseCase(career = career)){
                is ServerApiResponse.SuccessResponse -> {

                }
                is ServerApiResponse.ErrorResponse -> {}
                is ServerApiResponse.ExceptionResponse -> {}
            }
        }
    }

    private fun saveCarrier(career: SaveCarrierList){
        viewModelScope.launch(Dispatchers.IO) {
            when(val result = saveCarrierUseCase(career = career)){
                is ServerApiResponse.SuccessResponse -> {
                    _saveSuccessState.emit(Unit)
                }
                is ServerApiResponse.ErrorResponse -> {}
                is ServerApiResponse.ExceptionResponse -> {}
            }
        }
    }

    fun setCarrier(
        isMain: Boolean,
        year: String,
        main: String,
        sub: String,
        id: Int,
    ){
        println("호출 $isMain $year $main $sub $id")
        if(isMain){
            _state.update {
                it.copy(
                    getMainPosition = GetCarrier(
                        id = it.getMainPosition?.id ?: 0,
                        position = GetCarrierPosition(
                            id = id,
                            main = main,
                            sub = sub,
                        ),
                        years = year.split("년")[0].toInt(),
                        careerType = "primary",
                    ),
                    isShowPrevScreen = true
                )
            }
        } else {
            _state.update {
                it.copy(
                    getSubPosition = GetCarrier(
                        id = it.getSubPosition?.id ?: 0,
                        position = GetCarrierPosition(
                            id = id,
                            main = main,
                            sub = sub,
                        ),
                        years = year.split("년")[0].toInt(),
                        careerType = "secondary"
                    ),
                    isShowPrevScreen = true
                )
            }
        }
    }

    fun onAction(action: ProfileEditCareerAction){
        when(action){
            is ProfileEditCareerAction.OnChangePrevScreen -> { _state.update { it.copy(isShowPrevScreen = action.isShowPrevScreen, subPositionList = emptyList()) } }
            is ProfileEditCareerAction.OnChangeMainOrSub -> { _state.update { it.copy(isMainPosition = action.isMain) } }
            is ProfileEditCareerAction.OnGetSubPositionList -> { getSubPositionList(action.main) }
            is ProfileEditCareerAction.OnResetPrimaryPosition -> { _state.update { it.copy(getMainPosition = null) } }
            is ProfileEditCareerAction.OnResetSecondaryPosition -> { _state.update { it.copy(getSubPosition = null) } }
        }
    }
}