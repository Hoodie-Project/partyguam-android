package com.party.presentation.screen.profile_edit_portfolio.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.party.common.ServerApiResponse
import com.party.domain.model.user.profile.UserProfileRequest
import com.party.domain.usecase.user.profile.ModifyUserProfileUseCase
import com.party.presentation.screen.profile_edit_portfolio.ProfileEditPortfolioAction
import com.party.presentation.screen.profile_edit_portfolio.ProfileEditPortfolioState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileEditPortfolioViewModel @Inject constructor(
    private val modifyUserProfileUseCase: ModifyUserProfileUseCase,
): ViewModel(){

    private val _state = MutableStateFlow(ProfileEditPortfolioState())
    val state = _state.asStateFlow()

    private val _successState = MutableSharedFlow<Unit>()
    val successState = _successState.asSharedFlow()

    private fun modifyPortfolio(portfolioTitle: String, portfolio: String){
        viewModelScope.launch {
            when(val result = modifyUserProfileUseCase(
                userProfileRequest = UserProfileRequest(
                    portfolioTitle = portfolioTitle,
                    portfolio = portfolio
                )
            )){
                is ServerApiResponse.SuccessResponse -> { _successState.emit(Unit) }
                is ServerApiResponse.ErrorResponse -> {}
                is ServerApiResponse.ExceptionResponse -> {}
            }
        }
    }

    fun action(action: ProfileEditPortfolioAction){
        when(action){
            is ProfileEditPortfolioAction.OnChangeLink -> _state.update { it.copy(linkText = action.link) }
            is ProfileEditPortfolioAction.OnChangeTitle -> _state.update { it.copy(titleText = action.title) }
            is ProfileEditPortfolioAction.OnReset -> _state.update { it.copy(linkText = "", titleText = "") }
            is ProfileEditPortfolioAction.OnApply -> {
                modifyPortfolio(
                    portfolioTitle = _state.value.titleText,
                    portfolio = _state.value.linkText
                )
            }
            is ProfileEditPortfolioAction.OnDeleteLink -> _state.update { it.copy(linkText = "") }
            is ProfileEditPortfolioAction.OnDeleteTitle -> _state.update { it.copy(titleText = "") }
        }
    }
}