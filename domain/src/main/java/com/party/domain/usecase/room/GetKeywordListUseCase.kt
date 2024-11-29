package com.party.domain.usecase.room

import com.party.domain.repository.KeywordRepository
import javax.inject.Inject

class GetKeywordListUseCase @Inject constructor(
    private val keywordRepository: KeywordRepository,
) {
    operator fun invoke() = keywordRepository.getKeywordList()
}