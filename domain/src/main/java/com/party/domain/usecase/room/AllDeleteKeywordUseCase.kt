package com.party.domain.usecase.room

import com.party.domain.repository.KeywordRepository
import javax.inject.Inject

class AllDeleteKeywordUseCase @Inject constructor(
    private val keywordRepository: KeywordRepository,
) {
    suspend operator fun invoke() {
        keywordRepository.allDeleteKeyword()
    }
}