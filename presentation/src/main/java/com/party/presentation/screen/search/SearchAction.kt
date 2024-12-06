package com.party.presentation.screen.search

sealed interface SearchAction{
    data object OnNavigationBack: SearchAction
    data class OnInputKeywordChange(val keyword: String): SearchAction
    data class OnTabClick(val tabText: String): SearchAction
    data class OnIsShowKeywordAreaChange(val isShowKeywordArea: Boolean): SearchAction
    data object OnSearch: SearchAction
    data class OnDeleteKeyword(val keyword: String): SearchAction
    data object OnAllDeleteKeyword: SearchAction
}