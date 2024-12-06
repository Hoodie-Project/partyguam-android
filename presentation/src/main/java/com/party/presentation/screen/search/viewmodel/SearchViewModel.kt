package com.party.presentation.screen.search.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.party.common.ServerApiResponse
import com.party.domain.model.search.Search
import com.party.domain.model.search.SearchedParty
import com.party.domain.model.search.SearchedPartyRecruitment
import com.party.domain.usecase.room.AllDeleteKeywordUseCase
import com.party.domain.usecase.room.DeleteKeywordUseCase
import com.party.domain.usecase.room.GetKeywordListUseCase
import com.party.domain.usecase.room.InsertKeywordUseCase
import com.party.domain.usecase.search.GetSearchedDataUseCase
import com.party.presentation.screen.search.SearchAction
import com.party.presentation.screen.search.SearchState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val insertKeywordUseCase: InsertKeywordUseCase,
    private val getKeywordListUseCase: GetKeywordListUseCase,
    private val deleteKeywordUseCase: DeleteKeywordUseCase,
    private val allDeleteKeywordUseCase: AllDeleteKeywordUseCase,
    private val getSearchedDataUseCase: GetSearchedDataUseCase,
): ViewModel(){

    private val _searchState = MutableStateFlow(SearchState())
    val searchState = _searchState.asStateFlow()

    // 전체 영역 검색하기
    private fun allSearch(titleSearch: String, page: Int, limit: Int){
        viewModelScope.launch(Dispatchers.IO) {
            _searchState.update { it.copy(isLoadingAllSearch = true) }

            when(val result = getSearchedDataUseCase(titleSearch = titleSearch, page = page, limit = limit)){
                is ServerApiResponse.SuccessResponse -> {
                    _searchState.update {
                        it.copy(
                            isLoadingAllSearch = false,
                            allSearchedList = result.data ?: Search(
                                party = SearchedParty(total = 0, parties = emptyList()),
                                partyRecruitment = SearchedPartyRecruitment(total = 0, partyRecruitments = emptyList())
                            )
                        )
                    }
                }
                is ServerApiResponse.ErrorResponse -> {
                    _searchState.update { it.copy(isLoadingAllSearch = false) }
                }

                is ServerApiResponse.ExceptionResponse -> {
                    _searchState.update { it.copy(isLoadingAllSearch = false) }
                }
            }
        }
    }

    // 키워드 저장
    private fun insertKeyword(keyword: String){
        viewModelScope.launch(Dispatchers.IO) {
            insertKeywordUseCase(keyword = keyword)
        }
    }

    // 키워드 삭제
    private fun deleteKeyword(keyword: String){
        viewModelScope.launch(Dispatchers.IO) {
            deleteKeywordUseCase(keyword = keyword)
        }
    }

    // 키워드 전체 삭제
    private fun allDeleteKeyword(){
        viewModelScope.launch(Dispatchers.IO) {
            allDeleteKeywordUseCase()
        }
    }

    // 키워드 리스트 가져오기
    fun getKeywordList() {
        viewModelScope.launch(Dispatchers.IO) {
            getKeywordListUseCase().collectLatest { keywordList ->
                _searchState.update { it.copy(keywordList = keywordList)}
            }
        }
    }

    fun onAction(action: SearchAction){
        when(action){
            is SearchAction.OnNavigationBack -> { }
            is SearchAction.OnInputKeywordChange -> { _searchState.update { it.copy(inputKeyword = action.keyword) }}
            is SearchAction.OnTabClick -> { _searchState.update { it.copy(selectedTabText = action.tabText) }}
            is SearchAction.OnIsShowKeywordAreaChange -> { _searchState.update { it.copy(isShowKeywordArea = action.isShowKeywordArea) }}
            is SearchAction.OnSearch -> {
                _searchState.update { it.copy(isShowKeywordArea = false) } // 검색 버튼 클릭 시 키워드 영역을 숨긴다.
                insertKeyword(_searchState.value.inputKeyword)
                allSearch(_searchState.value.inputKeyword, 1, 50)
            }
            is SearchAction.OnDeleteKeyword -> { deleteKeyword(action.keyword) }
            is SearchAction.OnAllDeleteKeyword -> { allDeleteKeyword()}
        }
    }
}