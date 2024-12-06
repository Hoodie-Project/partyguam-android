package com.party.presentation.screen.search.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.party.common.ServerApiResponse
import com.party.domain.model.party.PartyList
import com.party.domain.model.party.RecruitmentList
import com.party.domain.model.search.Search
import com.party.domain.model.search.SearchedParty
import com.party.domain.model.search.SearchedPartyRecruitment
import com.party.domain.usecase.party.GetPartyListUseCase
import com.party.domain.usecase.party.GetRecruitmentListUseCase
import com.party.domain.usecase.room.AllDeleteKeywordUseCase
import com.party.domain.usecase.room.DeleteKeywordUseCase
import com.party.domain.usecase.room.GetKeywordListUseCase
import com.party.domain.usecase.room.InsertKeywordUseCase
import com.party.domain.usecase.search.GetSearchedDataUseCase
import com.party.presentation.enum.PartyType
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
    private val getPartyListUseCase: GetPartyListUseCase,
    private val getRecruitmentListUseCase: GetRecruitmentListUseCase,
) : ViewModel() {

    private val _searchState = MutableStateFlow(SearchState())
    val searchState = _searchState.asStateFlow()

    // 전체 영역 검색하기
    private fun allSearch(titleSearch: String, page: Int, limit: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _searchState.update { it.copy(isLoadingAllSearch = true) }

            when (val result =
                getSearchedDataUseCase(titleSearch = titleSearch, page = page, limit = limit)) {
                is ServerApiResponse.SuccessResponse -> {
                    _searchState.update {
                        it.copy(
                            isLoadingAllSearch = false,
                            allSearchedList = result.data ?: Search(party = SearchedParty(total = 0, parties = emptyList()), partyRecruitment = SearchedPartyRecruitment(total = 0, partyRecruitments = emptyList()))
                        )
                    }
                }
                is ServerApiResponse.ErrorResponse -> _searchState.update { it.copy(isLoadingAllSearch = false) }
                is ServerApiResponse.ExceptionResponse -> _searchState.update { it.copy(isLoadingAllSearch = false) }
            }
        }
    }

    // 파티 검색하기
    private fun partySearch(
        titleSearch: String,
        page: Int,
        size: Int,
        sort: String,
        order: String,
        status: String,
        partyTypes: List<Int> = emptyList(),
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            _searchState.update { it.copy(isLoadingParty = true) }

            when (val result = getPartyListUseCase(
                titleSearch = titleSearch,
                page = page,
                size = size,
                sort = sort,
                order = order,
                status = status,
                partyTypes = partyTypes
            )) {
                is ServerApiResponse.SuccessResponse -> _searchState.update { it.copy(isLoadingParty = false, partySearchedList = result.data ?: PartyList(total = 0, parties = emptyList())) }
                is ServerApiResponse.ErrorResponse -> _searchState.update { it.copy(isLoadingParty = false) }
                is ServerApiResponse.ExceptionResponse -> _searchState.update { it.copy(isLoadingParty = false) }
            }
        }
    }

    // 모집 공고 검색하기
    private fun recruitmentSearch(
        titleSearch: String,
        page: Int,
        size: Int,
        sort: String,
        order: String,
        partyTypes: List<Int> = emptyList(),
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            _searchState.update { it.copy(isLoadingRecruitment = true) }

            when (val result = getRecruitmentListUseCase(
                titleSearch = titleSearch,
                page = page,
                size = size,
                sort = sort,
                order = order,
                partyTypes = partyTypes
            )) {
                is ServerApiResponse.SuccessResponse -> {
                    _searchState.update {
                        it.copy(
                            isLoadingRecruitment = false,
                            recruitmentSearchedList = result.data ?: RecruitmentList(
                                total = 0,
                                partyRecruitments = emptyList()
                            )
                        )
                    }
                }

                is ServerApiResponse.ErrorResponse -> _searchState.update { it.copy(isLoadingRecruitment = false) }
                is ServerApiResponse.ExceptionResponse -> _searchState.update { it.copy(isLoadingRecruitment = false) }
            }
        }
    }

    // 키워드 저장
    private fun insertKeyword(keyword: String) {
        viewModelScope.launch(Dispatchers.IO) {
            insertKeywordUseCase(keyword = keyword)
        }
    }

    // 키워드 삭제
    private fun deleteKeyword(keyword: String) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteKeywordUseCase(keyword = keyword)
        }
    }

    // 키워드 전체 삭제
    private fun allDeleteKeyword() {
        viewModelScope.launch(Dispatchers.IO) {
            allDeleteKeywordUseCase()
        }
    }

    // 키워드 리스트 가져오기
    fun getKeywordList() {
        viewModelScope.launch(Dispatchers.IO) {
            getKeywordListUseCase().collectLatest { keywordList ->
                _searchState.update { it.copy(keywordList = keywordList) }
            }
        }
    }

    fun onAction(action: SearchAction) {
        when (action) {
            is SearchAction.OnNavigationBack -> {}
            is SearchAction.OnInputKeywordChange -> _searchState.update { it.copy(inputKeyword = action.keyword) }
            is SearchAction.OnTabClick -> _searchState.update { it.copy(selectedTabText = action.tabText) }
            is SearchAction.OnIsShowKeywordAreaChange -> { _searchState.update { it.copy(isShowKeywordArea = action.isShowKeywordArea) } }
            is SearchAction.OnSearch -> {
                _searchState.update { it.copy(isShowKeywordArea = false) } // 검색 버튼 클릭 시 키워드 영역을 숨긴다.
                insertKeyword(_searchState.value.inputKeyword)

                when (_searchState.value.selectedTabText) {
                    "전체" -> allSearch(titleSearch = _searchState.value.inputKeyword, page = 1, limit = 50)
                    "파티" -> partySearch(titleSearch = _searchState.value.inputKeyword, page = 1, size = 50, sort = "createdAt", order = "DESC", status = _searchState.value.isActiveParty)
                    "모집공고" -> recruitmentSearch(_searchState.value.inputKeyword, 1, size = 50, sort = "createdAt", order = "DESC")
                }
            }
            is SearchAction.OnDeleteKeyword -> deleteKeyword(action.keyword)
            is SearchAction.OnAllDeleteKeyword -> allDeleteKeyword()
            is SearchAction.OnChangeOrderByParty -> {
                _searchState.update { currentState ->
                    val sortedList = if (action.isDesc) {
                        currentState.partySearchedList.parties.sortedByDescending { it.createdAt }
                    } else {
                        currentState.partySearchedList.parties.sortedBy { it.createdAt }
                    }
                    currentState.copy(
                        isDescParty = action.isDesc,
                        partySearchedList = currentState.partySearchedList.copy(parties = sortedList)
                    )
                }
            }
            is SearchAction.OnChangeActive -> {
                _searchState.update { it.copy(isActiveParty = action.status) }
                partySearch(titleSearch = _searchState.value.inputKeyword, page = 1, size = 50, sort = "createdAt", order = "DESC", status = action.status)
            }
            is SearchAction.OnPartyTypeModelClose -> _searchState.update { it.copy(isPartyTypeSheetOpen = action.isVisibleModal)}

            // 클릭시 이미 저장되있으면 삭제하고 없으면 추가한다.
            is SearchAction.OnSelectedPartyType -> {
                _searchState.update { state ->
                    val updatedTypeList = state.selectedTypeListParty.toMutableList().apply {
                        if (action.partyType == "전체") {
                            clear() // "전체"가 들어오면 리스트를 전부 삭제
                            add("전체")
                        } else {
                            remove("전체") // "전체" 외의 값이 들어오면 "전체" 삭제
                            if (contains(action.partyType)) remove(action.partyType) else add(action.partyType)
                        }
                    }
                    state.copy(selectedTypeListParty = updatedTypeList)
                }
            }

            is SearchAction.OnPartyTypeReset -> {
                _searchState.update { it.copy(selectedTypeListParty = emptyList()) }
                _searchState.update { it.copy(selectedTypeListRecruitment = emptyList()) }
            }
            is SearchAction.OnPartyTypeApply -> {
                _searchState.update { it.copy(isPartyTypeSheetOpen = false) }

                val selectedTypeList: List<String> = _searchState.value.selectedTypeListParty
                partySearch(
                    titleSearch = _searchState.value.inputKeyword,
                    page = 1,
                    size = 50,
                    sort = "createdAt",
                    order = "DESC",
                    status = _searchState.value.isActiveParty,
                    partyTypes = selectedTypeList.mapNotNull { type ->
                        PartyType.entries.find { it.type == type }?.id
                    }
                )
            }
            is SearchAction.OnChangeOrderByRecruitment -> {
                _searchState.update { currentState ->
                    val sortedList = if (action.isDesc) {
                        currentState.recruitmentSearchedList.partyRecruitments.sortedByDescending { it.createdAt }
                    } else {
                        currentState.recruitmentSearchedList.partyRecruitments.sortedBy { it.createdAt }
                    }
                    currentState.copy(
                        isDescRecruitment = action.isDesc,
                        recruitmentSearchedList = currentState.recruitmentSearchedList.copy(partyRecruitments = sortedList)
                    )
                }
            }
            is SearchAction.OnPartyTypeRecruitment -> _searchState.update { it.copy(isPartyTypeSheetOpenRecruitment = action.isVisibleModal) }
            is SearchAction.OnSelectedPartyTypeRecruitment -> {
                _searchState.update { state ->
                    val updatedTypeList = state.selectedTypeListRecruitment.toMutableList().apply {
                        if (action.partyType == "전체") {
                            clear() // "전체"가 들어오면 리스트를 전부 삭제
                            add("전체")
                        } else {
                            remove("전체") // "전체" 외의 값이 들어오면 "전체" 삭제
                            if (contains(action.partyType)) remove(action.partyType) else add(action.partyType)
                        }
                    }
                    state.copy(selectedTypeListRecruitment = updatedTypeList)
                }
            }
            is SearchAction.OnPartyTypeApply2 -> {
                _searchState.update { it.copy(
                    isPartyTypeSheetOpenRecruitment = false,
                    selectedTypeListSize = _searchState.value.selectedTypeListRecruitment.size
                ) }

                val selectedTypeList: List<String> = _searchState.value.selectedTypeListRecruitment
                recruitmentSearch(
                    titleSearch = _searchState.value.inputKeyword,
                    page = 1,
                    size = 50,
                    sort = "createdAt",
                    order = "DESC",
                    partyTypes = selectedTypeList.mapNotNull { type ->
                        PartyType.entries.find { it.type == type }?.id
                    }
                )
            }
        }
    }
}
