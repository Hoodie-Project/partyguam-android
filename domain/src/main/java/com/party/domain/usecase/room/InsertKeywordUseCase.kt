package com.party.domain.usecase.room

import com.party.domain.model.room.toKeywordModel
import com.party.domain.repository.KeywordRepository
import javax.inject.Inject

class InsertKeywordUseCase @Inject constructor(
    private val keywordRepository: KeywordRepository,
){
    operator fun invoke(keyword: String) = keywordRepository.insertKeyword(keyword.toKeywordModel(keyword = keyword))
}