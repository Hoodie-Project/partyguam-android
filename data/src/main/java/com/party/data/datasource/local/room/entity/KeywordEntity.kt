package com.party.data.datasource.local.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.party.common.Constants.KEYWORD_ENTITY
import com.party.domain.model.room.KeywordModel

@Entity(tableName = KEYWORD_ENTITY)
data class KeywordEntity(
    @PrimaryKey
    val keyword: String,
)

fun KeywordModel.toKeywordEntity(): KeywordEntity {
    return KeywordEntity(
        keyword = keyword,
    )
}

fun KeywordEntity.toKeywordModel(): KeywordModel {
    return KeywordModel(
        keyword = keyword,
    )
}