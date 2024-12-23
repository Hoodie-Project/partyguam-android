package com.party.presentation.screen.recruitment_detail

import com.party.domain.model.party.RecruitmentDetail
import com.party.domain.model.party.RecruitmentDetailParty
import com.party.domain.model.party.RecruitmentDetailPartyType
import com.party.domain.model.party.RecruitmentDetailPosition

data class RecruitmentDetailState(
    val isLoading: Boolean = false,
    val recruitmentDetail: RecruitmentDetail = RecruitmentDetail(
        party = RecruitmentDetailParty(
            title = "",
            image = "",
            status = "",
            partyType = RecruitmentDetailPartyType(type = "")
        ),
        position = RecruitmentDetailPosition(
            id = 0,
            main = "",
            sub = ""
        ),
        content = "",
        recruitingCount = 0,
        recruitedCount = 0,
        applicationCount = 0,
        createdAt = "2024-06-05T15:30:45.123Z"
    ),
)
