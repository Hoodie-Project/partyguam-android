package com.party.presentation.screen.recruitment_create_preview

import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

data class RecruitmentCreatePreviewState(

    val networkImage: String = "",
    val partyStatus: String = "", // 파티 상태

    val inputPartyTitle: String = "", // 파티 제목

    val selectedPartyType: String = "", // 선택된 파티 타입 (미정, 스터디, 포트폴리오, 해커톤, 공모전)

    val partyId: Int = 0,

    val recruitingCount: Int = 0,
    val recruitedCount: Int = 0,
    val applicationCount: Int = 0,
    val recruitmentDescription: String = "",
    val main: String = "",
    val sub: String = "",

    val createdAt: String = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX").withZone(ZoneOffset.UTC).format(Instant.now())
)
