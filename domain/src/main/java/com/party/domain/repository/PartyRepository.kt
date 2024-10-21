package com.party.domain.repository

import com.party.common.ServerApiResponse
import com.party.domain.model.party.PartyListResponse
import com.party.domain.model.party.PersonalRecruitmentListResponse
import com.party.domain.model.party.RecruitmentListResponse

interface PartyRepository {

    // 홈화면 - 개인 맞춤 추천 모집공고 리스트
    suspend fun getPersonalizedParties(
        page: Int,
        size: Int,
        sort: String,
        order: String
    ): ServerApiResponse<PersonalRecruitmentListResponse>

    // 홈화면 - 모집공고 리스트
    suspend fun getRecruitmentList(
        page: Int,
        size: Int,
        sort: String,
        order: String
    ): ServerApiResponse<RecruitmentListResponse>

    // 파티 리스트 조회
    suspend fun getPartyList(
        page: Int,
        size: Int,
        sort: String,
        order: String
    ): ServerApiResponse<PartyListResponse>
}
