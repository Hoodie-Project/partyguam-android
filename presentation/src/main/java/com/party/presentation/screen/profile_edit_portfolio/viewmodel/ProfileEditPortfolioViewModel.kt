package com.party.presentation.screen.profile_edit_portfolio.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.party.presentation.screen.profile_edit_portfolio.ProfileEditPortfolioAction
import com.party.presentation.screen.profile_edit_portfolio.ProfileEditPortfolioState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileEditPortfolioViewModel @Inject constructor(

): ViewModel(){

    private val _state = MutableStateFlow(ProfileEditPortfolioState())
    val state = _state.asStateFlow()

    private fun modifyPortfolio(){
        viewModelScope.launch {

        }
    }

    fun action(action: ProfileEditPortfolioAction){
        when(action){
            is ProfileEditPortfolioAction.OnChangeLink -> _state.update { it.copy(linkText = action.link) }
            is ProfileEditPortfolioAction.OnChangeTitle -> _state.update { it.copy(titleText = action.title) }
            is ProfileEditPortfolioAction.OnReset -> _state.update { it.copy(linkText = "", titleText = "") }
            is ProfileEditPortfolioAction.OnApply -> {
                modifyPortfolio()
            }
            is ProfileEditPortfolioAction.OnDeleteLink -> _state.update { it.copy(linkText = "") }
            is ProfileEditPortfolioAction.OnDeleteTitle -> _state.update { it.copy(titleText = "") }
        }
    }
}