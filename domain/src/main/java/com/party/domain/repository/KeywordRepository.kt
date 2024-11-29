package com.party.domain.repository

import com.party.domain.model.room.KeywordModel
import kotlinx.coroutines.flow.Flow

interface KeywordRepository {

    fun insertKeyword(keywordModel: KeywordModel)

    fun deleteKeyword(keyword: String)

    fun allDeleteKeyword()

    fun getKeywordList(): Flow<List<KeywordModel>>
}