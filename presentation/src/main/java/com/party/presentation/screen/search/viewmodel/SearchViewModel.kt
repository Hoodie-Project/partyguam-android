package com.party.presentation.screen.search.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.party.common.ServerApiResponse
import com.party.common.UIState
import com.party.domain.model.room.KeywordModel
import com.party.domain.model.search.Search
import com.party.domain.usecase.room.AllDeleteKeywordUseCase
import com.party.domain.usecase.room.DeleteKeywordUseCase
import com.party.domain.usecase.room.GetKeywordListUseCase
import com.party.domain.usecase.room.InsertKeywordUseCase
import com.party.domain.usecase.search.GetSearchedDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
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

    private val _keywordList = MutableStateFlow<List<KeywordModel>>(emptyList())
    val keywordList: StateFlow<List<KeywordModel>> = _keywordList

    private val _searchedDataState = MutableStateFlow<UIState<ServerApiResponse<Search>>>(UIState.Idle)
    val searchedDataState: StateFlow<UIState<ServerApiResponse<Search>>> = _searchedDataState

    // 검색하기
    fun search(titleSearch: String, page: Int, limit: Int){
        viewModelScope.launch(Dispatchers.IO) {
            _searchedDataState.emit(UIState.Loading)
            when(val result = getSearchedDataUseCase(titleSearch = titleSearch, page = page, limit = limit)){
                is ServerApiResponse.SuccessResponse -> {
                    _searchedDataState.emit(UIState.Success(result))
                }
                is ServerApiResponse.ErrorResponse -> {
                    _searchedDataState.emit(UIState.Error(result))
                }
                is ServerApiResponse.ExceptionResponse -> {
                    _searchedDataState.emit(UIState.Exception)
                }
            }
        }
    }

    // 키워드 저장
    fun insertKeyword(keyword: String){
        viewModelScope.launch(Dispatchers.IO) {
            insertKeywordUseCase(keyword = keyword)
        }
    }

    // 키워드 삭제
    fun deleteKeyword(keyword: String){
        viewModelScope.launch(Dispatchers.IO) {
            deleteKeywordUseCase(keyword = keyword)
        }
    }

    // 키워드 전체 삭제
    fun allDeleteKeyword(){
        viewModelScope.launch(Dispatchers.IO) {
            allDeleteKeywordUseCase()
        }
    }

    // 키워드 리스트 가져오기
    fun getKeywordList() {
        viewModelScope.launch(Dispatchers.IO) {
            getKeywordListUseCase().collectLatest {
                _keywordList.emit(it)
            }
        }
    }
}