package com.party.presentation.screen.manage_applicant.viewmodel

import androidx.lifecycle.ViewModel
import com.party.presentation.screen.manage_applicant.ManageApplicantState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ManageApplicantViewModel @Inject constructor(

): ViewModel(){

    private val _state = MutableStateFlow(ManageApplicantState())
    val state = _state.asStateFlow()
}