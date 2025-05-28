package com.party.presentation.enum

import androidx.compose.ui.graphics.Color
import com.party.guam.design.ACTIVE_CHIP_BACKGROUND
import com.party.guam.design.ACTIVE_CHIP_TEXT
import com.party.guam.design.ARCHIVED_CHIP_BACKGROUND
import com.party.guam.design.ARCHIVED_CHIP_TEXT

enum class StatusType(val type: String) {
    ACTIVE("active"),
    ARCHIVED("archived");

    companion object {
        fun fromType(type: String): StatusType {
            return entries.find { it.type == type } ?: ARCHIVED // 기본값을 ARCHIVED로 설정
        }

        // "진행중"이나 "종료"를 입력하면 type 반환
        fun fromDisplayText(displayText: String): String {
            return when (displayText) {
                "진행중" -> ACTIVE.type
                "종료" -> ARCHIVED.type
                else -> ACTIVE.type
            }
        }
    }

    // StatusType을 DisplayText로 반환
    fun toDisplayText(): String {
        return when (this) {
            ACTIVE -> "진행중"
            ARCHIVED -> "종료"
        }
    }

    fun toContainerColor(): Color {
        return when(this){
            ACTIVE -> ACTIVE_CHIP_BACKGROUND
            ARCHIVED -> ARCHIVED_CHIP_BACKGROUND
        }
    }

    fun toContentColor(): Color {
        return when(this){
            ACTIVE -> ACTIVE_CHIP_TEXT
            ARCHIVED -> ARCHIVED_CHIP_TEXT
        }
    }

}