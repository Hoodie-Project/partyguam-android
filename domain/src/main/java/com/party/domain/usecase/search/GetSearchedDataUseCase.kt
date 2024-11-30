package com.party.domain.usecase.search

import com.party.domain.repository.SearchRepository
import javax.inject.Inject

class GetSearchedDataUseCase @Inject constructor(
    private val searchRepository: SearchRepository
) {
    suspend operator fun invoke(titleSearch: String, page: Int, limit: Int) = searchRepository.searchPartiesByKeyword(titleSearch, page, limit)
}