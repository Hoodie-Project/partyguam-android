package com.party.presentation.shared

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(

): ViewModel() {

    var isScrollPartyArea by mutableStateOf(false)

    var isScrollRecruitmentArea by mutableStateOf(false)

    private val _scrollToUp = MutableSharedFlow<Unit>()
    val scrollToUp = _scrollToUp.asSharedFlow()

    fun scrollToTopFun(){
        viewModelScope.launch(Dispatchers.Main) {
            _scrollToUp.emit(Unit)
        }
    }
}