package com.party.data.datasource.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.party.data.datasource.local.room.entity.KeywordEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface KeywordDao {

    // 키워드 추가
    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertKeyword(keywordEntity: KeywordEntity)

    // 키워드 삭제
    @Query("DELETE FROM keyword_entity WHERE keyword =:keyword")
    fun deleteKeyword(keyword : String)

    // 키워드 전체 삭제
    @Query("DELETE FROM keyword_entity")
    fun allDeleteKeyword()

    // 키워드 최근 5개 조회
    @Query("SELECT * FROM keyword_entity ORDER BY keyword DESC LIMIT 5")
    fun getKeywordList() : Flow<List<KeywordEntity>>
}