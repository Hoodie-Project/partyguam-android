package com.party.data.datasource.remote.party

import com.party.data.dto.party.PartyApplyDto
import com.party.data.dto.party.PartyAuthorityDto
import com.party.data.dto.party.PartyCreateDto
import com.party.data.dto.party.PartyDetailDto
import com.party.data.dto.party.PartyListDto
import com.party.data.dto.party.PartyRecruitmentDto
import com.party.data.dto.party.PartyUsersDto
import com.party.data.dto.party.PersonalRecruitmentListDto
import com.party.data.dto.party.RecruitmentCreateDto
import com.party.data.dto.party.RecruitmentDetailDto
import com.party.data.dto.party.RecruitmentListDto
import com.party.domain.model.party.PartyApplyRequest
import com.party.domain.model.party.RecruitmentCreateRequest
import com.skydoves.sandwich.ApiResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface PartyRemoteSource {

    // 홈화면 - 개인 맞춤 추천 모집공고 리스트
    suspend fun getPersonalizedParties(page: Int, size: Int, sort: String, order: String): ApiResponse<PersonalRecruitmentListDto>

    // 홈화면 - 모집공고 리스트 조회
    suspend fun getRecruitmentList(page: Int, size: Int, sort: String, order: String, titleSearch: String?, partyTypes: List<Int>, position: List<Int>): ApiResponse<RecruitmentListDto>

    // 홈화면 - 파티 리스트 조회
    suspend fun getPartyList(page: Int, size: Int, sort: String, order: String, partyTypes: List<Int>, titleSearch: String?, status: String?): ApiResponse<PartyListDto>

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