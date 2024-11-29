package com.party.presentation.screen.search.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.party.domain.model.room.KeywordModel
import com.party.domain.usecase.room.AllDeleteKeywordUseCase
import com.party.domain.usecase.room.DeleteKeywordUseCase
import com.party.domain.usecase.room.GetKeywordListUseCase
import com.party.domain.usecase.room.InsertKeywordUseCase
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
): ViewModel(){

    private val _keywordList = MutableStateFlow<List<KeywordModel>>(emptyList())
    val keywordList: StateFlow<List<KeywordModel>> = _keywordList

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