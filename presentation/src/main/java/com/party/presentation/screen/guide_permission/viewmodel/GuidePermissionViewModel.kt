package com.party.presentation.screen.guide_permission.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.party.domain.usecase.datastore.SaveFirstLaunchFlagUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GuidePermissionViewModel @Inject constructor(
    private val saveFirstLaunchFlagUseCase: SaveFirstLaunchFlagUseCase
): ViewModel() {

    fun saveFirstLaunchFlag(){
        viewModelScope.launch(Dispatchers.IO) {
            saveFirstLaunchFlagUseCase()
        }
    }
}