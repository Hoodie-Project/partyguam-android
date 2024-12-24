package com.party.presentation.enum

enum class GenderType(val type: String) {
    MAN("M"),
    WOMAN("W");

    companion object {
        fun fromType(type: String): GenderType {
            return entries.find { it.type == type } ?: MAN
        }
    }

    fun toDisplayText(): String {
        return when (this) {
            MAN -> "남자"
            WOMAN -> "여자"
        }
    }
}