package com.party.presentation.enum

enum class StatusType(val type: String) {
    ACTIVE("active"),
    ARCHIVED("archived");

    companion object {
        fun fromType(type: String): StatusType {
            return entries.find { it.type == type } ?: ARCHIVED // 기본값을 ARCHIVED로 설정
        }
    }

    fun toDisplayText(): String = if (this == ACTIVE) "진행중" else "종료"
}