package com.party.domain.repository

import com.party.common.ServerApiResponse
import com.party.domain.model.party.PersonalRecruitmentListResponse

interface PartyRepository {

    // 홈화면 - 개인 맞춤 추천 모집공고 리스트
    suspend fun getPersonalizedParties(page: Int, size: Int, sort: String, order: String): ServerApiResponse<PersonalRecruitmentListResponse>
}