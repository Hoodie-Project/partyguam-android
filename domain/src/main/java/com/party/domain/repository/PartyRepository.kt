package com.party.domain.repository

import com.party.common.ServerApiResponse
import com.party.domain.model.party.PartyCreate
import com.party.domain.model.party.PartyCreateRequest
import com.party.domain.model.party.PartyDetail
import com.party.domain.model.party.PartyList
import com.party.domain.model.party.PartyRecruitment
import com.party.domain.model.party.PartyUsers
import com.party.domain.model.party.PersonalRecruitmentList
import com.party.domain.model.party.RecruitmentDetail
import com.party.domain.model.party.RecruitmentList
import com.party.domain.model.user.PartyAuthority

interface PartyRepository {

    // 홈화면 - 개인 맞춤 추천 모집공고 리스트
    suspend fun getPersonalizedParties(
        page: Int,
        size: Int,
        sort: String,
        order: String
    ): ServerApiResponse<PersonalRecruitmentList>

    // 홈화면 - 모집공고 리스트
    suspend fun getRecruitmentList(
        page: Int,
        size: Int,
        sort: String,
        order: String
    ): ServerApiResponse<RecruitmentList>

    // 파티 리스트 조회
    suspend fun getPartyList(
        page: Int,
        size: Int,
        sort: String,
        order: String,
        partyTypes: List<Int>,
    ): ServerApiResponse<PartyList>

    // 모집공고 상세 조회
    suspend fun getRecruitmentDetail(
        partyRecruitmentId: Int
    ): ServerApiResponse<RecruitmentDetail>

    // 파티 상세 조회
    suspend fun getPartyDetail(partyId: Int): ServerApiResponse<PartyDetail>

    // 파티 상세 조회 - 파티원 리스트 조회
    suspend fun getPartyUsers(partyId: Int, page: Int, limit: Int, sort: String, order: String): ServerApiResponse<PartyUsers>

    // 파티 상세 조회 - 모집 공고 리스트 조회
    suspend fun getPartyRecruitmentList(partyId: Int, sort: String, order: String, main: String?): ServerApiResponse<List<PartyRecruitment>>

    // 파티 상세 조회 - 나의 파티 권한 조회
    suspend fun getPartyAuthority(partyId: Int): ServerApiResponse<PartyAuthority>

    // 파티 생성
    suspend fun saveParty(partyCreateRequest: PartyCreateRequest): ServerApiResponse<PartyCreate>
}
