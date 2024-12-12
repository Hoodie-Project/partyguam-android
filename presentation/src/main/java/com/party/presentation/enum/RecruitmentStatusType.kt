package com.party.presentation.enum

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Yellow
import com.party.common.ui.theme.BLACK
import com.party.common.ui.theme.GRAY400
import com.party.common.ui.theme.PRIMARY

enum class RecruitmentStatusType(val status: String) {
    PENDING("pending"),
    PROCESSING("processing"),
    ACTIVE("active"),
    ARCHIVED("archived");

    companion object{
        fun fromStatus(status: String): RecruitmentStatusType {
            return entries.find { it.status == status } ?: ARCHIVED
        }

        // "검토중"이나 "응답대기"를 입력하면 status 반환
        fun fromDisplayText(displayText: String): String {
            return when(displayText){
                "검토중" -> PENDING.status
                "응답대기" -> PROCESSING.status
                "수락" -> ACTIVE.status
                "거절" -> ARCHIVED.status
                else -> PENDING.status
            }
        }
    }

    fun toDisplayText(): String {
        return when(this){
            PENDING -> "검토중"
            PROCESSING -> "응답대기"
            ACTIVE -> "수락"
            ARCHIVED -> "거절"
        }
    }

    // 컬러 반환 함수 추가
    fun toColor(): Color {
        return when (this) {
            PENDING -> BLACK
            PROCESSING -> Yellow
            ACTIVE -> PRIMARY
            ARCHIVED -> GRAY400
        }
    }
}