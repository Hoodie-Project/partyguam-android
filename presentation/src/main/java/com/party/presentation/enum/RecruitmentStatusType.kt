package com.party.presentation.enum

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Yellow
import com.party.common.ui.theme.BLACK
import com.party.common.ui.theme.GRAY400
import com.party.common.ui.theme.PRIMARY

// 검토중 : 양쪽다 수락하지 않은 상태
// 응답대기 : 파티장이 수락하고 파티원이 수락하지 않은 상태
enum class RecruitmentStatusType(val status: String) {
    PENDING("pending"),
    PROCESSING("processing"),
    APPROVED("approved"),
    REJECTED("rejected");

    companion object{
        fun fromStatus(status: String): RecruitmentStatusType {
            return entries.find { it.status == status } ?: REJECTED
        }

        // "검토중"이나 "응답대기"를 입력하면 status 반환
        fun fromDisplayText(displayText: String): String {
            return when(displayText){
                "검토중" -> PENDING.status
                "응답대기" -> PROCESSING.status
                "수락" -> APPROVED.status
                "거절" -> REJECTED.status
                else -> PENDING.status
            }
        }
    }

    fun toDisplayText(): String {
        return when(this){
            PENDING -> "검토중"
            PROCESSING -> "응답대기"
            APPROVED -> "수락"
            REJECTED -> "거절"
        }
    }

    // 컬러 반환 함수 추가
    fun toColor(): Color {
        return when (this) {
            PENDING -> BLACK
            PROCESSING -> Yellow
            APPROVED -> PRIMARY
            REJECTED -> GRAY400
        }
    }
}