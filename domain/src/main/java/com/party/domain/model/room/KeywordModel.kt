package com.party.domain.model.room

data class KeywordModel(
    val keyword: String,
)

fun String.toKeywordModel(keyword: String): KeywordModel {
    return KeywordModel(
        keyword = keyword,
    )
}