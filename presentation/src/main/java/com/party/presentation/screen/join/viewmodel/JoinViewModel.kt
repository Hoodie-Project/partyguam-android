package com.party.presentation.screen.join.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.party.core.domain.DataErrorRemote
import com.party.core.domain.onError
import com.party.core.domain.onSuccess
import com.party.domain.model.user.signup.UserSignUpRequest
import com.party.domain.usecase.user.auth.CheckNickNameUseCase
import com.party.domain.usecase.user.auth.UserSignUpUseCase
import com.party.presentation.screen.join.action.JoinAction
import com.party.presentation.screen.join.state.JoinState
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
class JoinViewModel @Inject constructor(
    private val checkNickNameUseCase: CheckNickNameUseCase,
    private val userSignUpUseCase: UserSignUpUseCase,
): ViewModel() {

    private val _state = MutableStateFlow(JoinState())
    val state = _state.asStateFlow()

    private val _goToBirthDayScreen = MutableSharedFlow<Unit>()
    val goToBirthDayScreen = _goToBirthDayScreen.asSharedFlow()

    private val _goToJoinCompleteScreen = MutableSharedFlow<Unit>()
    val goToJoinCompleteScreen = _goToJoinCompleteScreen.asSharedFlow()

    fun setEmailAndSignupAccessToken(
        email: String,
        signupAccessToken: String,
    ){
        _state.update { it.copy(email = email, signupAccessToken = signupAccessToken) }
    }

    // 닉네임 중복체크
    fun checkUserNickName(
        nickname: String
    ){
        viewModelScope.launch(Dispatchers.IO) {
            checkNickNameUseCase(
                signupAccessToken = "Bearer ${_state.value.signupAccessToken}" ,
                nickname = nickname,
            )
                .onSuccess {
                    _goToBirthDayScreen.emit(Unit)
                }
                .onError { error ->
                    when (error) {
                        is DataErrorRemote.Conflict -> _state.update { it.copy(warningMessage = "중복된 닉네임이에요.") }
                        else -> {}
                    }

                }
        }
    }

    // 회원가입
    fun join(){
        viewModelScope.launch(Dispatchers.IO) {
            userSignUpUseCase(
                signupAccessToken = "Bearer ${_state.value.signupAccessToken}" ,
                userSignUpRequest = UserSignUpRequest(
                    nickname = _state.value.userNickName,
                    birth = _state.value.birthDay,
                    gender = formatGender(
                        _state.value.gender
                    ),
                )
            ).onSuccess {
                _goToJoinCompleteScreen.emit(Unit)
            }.onError { error ->

            }
        }
    }


    fun onAction(action: JoinAction){
        when(action){
            is JoinAction.OnShowCancelJoinDialog -> _state.update { it.copy(isShowCancelJoinDialog = action.isShow) }
            is JoinAction.OnInputNickName ->{
                _state.update {
                    it.copy(
                        userNickName = action.nickName,
                        warningMessage = convertWarningText(action.nickName),
                    )
                }
            }
            is JoinAction.OnResetNickName -> _state.update { it.copy(userNickName = "", warningMessage = "") }
            is JoinAction.OnCheckNickName -> checkUserNickName(nickname = _state.value.userNickName)
            is JoinAction.OnInputBirthDay -> _state.update { it.copy(birthDay = action.birthDay) }
            is JoinAction.OnSelectGender -> _state.update { it.copy(gender = action.gender) }
            is JoinAction.OnJoinComplete -> join()
        }
    }

    fun convertWarningText(userNickName: String): String{
        return if(containsSpecialCharacters(userNickName)) "특수문자는 사용할 수 없어요."
        else if(userNickName.isEmpty()) ""
        else if(userNickName.length > 15 || userNickName.length < 2) "닉네임은 2자 이상 15자 이내로 입력해주세요"
        else ""
    }

    fun containsSpecialCharacters(input: String): Boolean {
        // 한글, 알파벳, 숫자를 제외한 특수문자를 확인하기 위한 정규식
        val regex = Regex("[^가-힣a-zA-Z0-9]") // 한글, 알파벳, 숫자가 아닌 문자를 찾음
        return regex.containsMatchIn(input)
    }

    private fun formatGender(selectedGender: String): String{
        return if (selectedGender == "남자") "M" else "F"
    }
}