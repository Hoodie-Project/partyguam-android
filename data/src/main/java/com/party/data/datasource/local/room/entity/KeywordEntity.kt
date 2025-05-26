package com.party.data.datasource.local.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.party.common.Constants.KEYWORD_ENTITY
import com.party.domain.model.room.KeywordModel

@Entity(tableName = KEYWORD_ENTITY)
data class KeywordEntity(
    @PrimaryKey
    val keyword: String,

    val createDate: Long = System.currentTimeMillis()
)

fun KeywordModel.toKeywordEntity(): KeywordEntity {
    return KeywordEntity(
        keyword = keyword,
        createDate = System.currentTimeMillis()
    )
}

fun KeywordEntity.toKeywordModel(): KeywordModel {
    return KeywordModel(
        keyword = keyword
    )
}