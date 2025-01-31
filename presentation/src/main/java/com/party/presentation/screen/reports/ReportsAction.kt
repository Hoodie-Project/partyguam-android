package com.party.presentation.screen.reports

sealed interface ReportsAction {
    data class OnChangeInputText(val inputText: String): ReportsAction
    data object OnAllDeleteInputText: ReportsAction
    data class OnReports(val typeId: Int): ReportsAction
}