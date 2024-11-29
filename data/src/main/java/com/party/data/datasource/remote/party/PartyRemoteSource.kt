package com.party.data.datasource.remote.party

import com.party.data.entity.party.PartyApplyDto
import com.party.data.entity.party.PartyAuthorityDto
import com.party.data.entity.party.PartyCreateDto
import com.party.data.entity.party.PartyDetailDto
import com.party.data.entity.party.PartyListDto
import com.party.data.entity.party.PartyRecruitmentDto
import com.party.data.entity.party.PartyUsersDto
import com.party.data.entity.party.PersonalRecruitmentListDto
import com.party.data.entity.party.RecruitmentCreateDto
import com.party.data.entity.party.RecruitmentDetailDto
import com.party.data.entity.party.RecruitmentListDto
import com.party.domain.model.party.PartyApplyRequest
import com.party.domain.model.party.RecruitmentCreateRequest
import com.skydoves.sandwich.ApiResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface PartyRemoteSource {

    // 홈화면 - 개인 맞춤 추천 모집공고 리스트
    suspend fun getPersonalizedParties(page: Int, size: Int, sort: String, order: String): ApiResponse<PersonalRecruitmentListDto>

    // 홈화면 - 모집공고 리스트 조회
    suspend fun getRecruitmentList(page: Int, size: Int, sort: String, order: String): ApiResponse<RecruitmentListDto>

    // 홈화면 - 파티 리스트 조회
    suspend fun getPartyList(page: Int, size: Int, sort: String, order: String, partyTypes: List<Int>): ApiResponse<PartyListDto>

    // 모집공고 상세 조회
    suspend fun getRecruitmentDetail(partyRecruitmentId: Int): ApiResponse<RecruitmentDetailDto>

    // 파티 상세 조회
    suspend fun getPartyDetail(partyId: Int): ApiResponse<PartyDetailDto>

    // 파티 상세 조회 - 파티원 조회
    suspend fun getPartyUsers(partyId: Int, page: Int, limit: Int, sort: String, order: String): ApiResponse<PartyUsersDto>

    // 파티 상세 조회 - 모집 공고 리스트 조회
    suspend fun getPartyRecruitmentList(partyId: Int, sort: String, order: String, main: String?): ApiResponse<List<PartyRecruitmentDto>>

    // 파티 상세 조회 - 나의 파티 권한 조회
    suspend fun getPartyAuthority(partyId: Int): ApiResponse<PartyAuthorityDto>

    // 파티 생성
    suspend fun createParty(
        title: RequestBody,
        content: RequestBody,
        partyTypeId: RequestBody,
        positionId: RequestBody,
        image: MultipartBody.Part
    ): ApiResponse<PartyCreateDto>

    // 파티지원하기 - 모집공고 지원
    suspend fun applyPartyRecruitment(partyRecruitmentId: Int, partyId: Int, partyApplyRequest: PartyApplyRequest): ApiResponse<PartyApplyDto>

    // 모집공고 추가하기
    suspend fun createRecruitment(partyId: Int, recruitmentCreateRequest: RecruitmentCreateRequest): ApiResponse<RecruitmentCreateDto>
}