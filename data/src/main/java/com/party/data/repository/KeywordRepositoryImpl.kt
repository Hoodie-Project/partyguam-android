package com.party.data.repository

import com.party.data.datasource.local.room.dao.KeywordDao
import com.party.data.datasource.local.room.entity.toKeywordEntity
import com.party.data.datasource.local.room.entity.toKeywordModel
import com.party.domain.model.room.KeywordModel
import com.party.domain.repository.KeywordRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class KeywordRepositoryImpl @Inject constructor(
    private val keywordDao: KeywordDao,
): KeywordRepository {
    override fun insertKeyword(keywordModel: KeywordModel) {
        println("insertKeyword: $keywordModel")
        keywordDao.insertKeyword(keywordModel.toKeywordEntity())
    }

    override fun deleteKeyword(keyword: String) {
        keywordDao.deleteKeyword(keyword = keyword)
    }

    override fun allDeleteKeyword() {
        keywordDao.allDeleteKeyword()
    }

    override fun getKeywordList(): Flow<List<KeywordModel>> {
        return keywordDao.getKeywordList().map { keywordEntityList ->
            keywordEntityList.map { it.toKeywordModel() }
        }
    }
}