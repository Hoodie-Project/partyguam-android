package com.party.data.datasource.remote.party

import com.party.data.dto.party.ApprovalAndRejectionDto
import com.party.data.dto.party.CheckUserApplicationStatusDto
import com.party.data.dto.party.PartyApplyDto
import com.party.data.dto.party.PartyAuthorityDto
import com.party.data.dto.party.PartyCreateDto
import com.party.data.dto.party.PartyDetailDto
import com.party.data.dto.party.PartyListDto
import com.party.data.dto.party.PartyMembersInfoDto
import com.party.data.dto.party.PartyModifyDto
import com.party.data.dto.party.PartyRecruitmentCompletedDto
import com.party.data.dto.party.PartyRecruitmentDto
import com.party.data.dto.party.PartyUsersDto
import com.party.data.dto.party.PersonalRecruitmentListDto
import com.party.data.dto.party.RecruitmentApplicantDto
import com.party.data.dto.party.RecruitmentCreateDto
import com.party.data.dto.party.RecruitmentDetailDto
import com.party.data.dto.party.RecruitmentListDto
import com.party.domain.model.party.DelegatePartyMasterRequest
import com.party.domain.model.party.ModifyPartyUserPositionRequest
import com.party.domain.model.party.ModifyRecruitmentRequest
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
    suspend fun getPartyRecruitmentList(partyId: Int, sort: String, order: String, main: String?, status: String?): ApiResponse<List<PartyRecruitmentDto>>

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

    // 파티 수정
    suspend fun modifyParty(
        partyId: Int,
        title: RequestBody?,
        content: RequestBody?,
        partyTypeId: RequestBody?,
        image: MultipartBody.Part?,
        status: RequestBody?
    ): ApiResponse<PartyModifyDto>

    // 파티지원하기 - 모집공고 지원
    suspend fun applyPartyRecruitment(partyRecruitmentId: Int, partyId: Int, partyApplyRequest: PartyApplyRequest): ApiResponse<PartyApplyDto>

    // 모집공고 추가하기
    suspend fun createRecruitment(partyId: Int, recruitmentCreateRequest: RecruitmentCreateRequest): ApiResponse<RecruitmentCreateDto>

    // 관리자 - 파티원 리스트 조회
    suspend fun getPartyMembers(partyId: Int, page: Int, limit: Int, sort: String, order: String, main: String?, nickname: String?): ApiResponse<PartyMembersInfoDto>

    // 관리자 - 파티원 포지션 변경
    suspend fun modifyPartyMemberPosition(partyId: Int, partyUserId: Int, modifyPartyUserPositionRequest: ModifyPartyUserPositionRequest): ApiResponse<Unit>

    // 관리자 - 파티 삭제
    suspend fun deleteParty(partyId: Int): ApiResponse<Unit>

    // 관리자 - 파티원 내보내기
    suspend fun deletePartyMember(partyId: Int, partyUserId: Int): ApiResponse<Unit>

    // 관리자 - 파티장 위임하기
    suspend fun changeMaster(partyId: Int, delegatePartyMasterRequest: DelegatePartyMasterRequest,): ApiResponse<Unit>

    // 관리자 - 파티모집 삭제
    suspend fun deleteRecruitment(partyId: Int, partyRecruitmentId: Int): ApiResponse<Unit>

    // 관리자 - 파티모집 수정
    suspend fun modifyRecruitment(partyId: Int, partyRecruitmentId: Int, modifyRecruitmentRequest: ModifyRecruitmentRequest): ApiResponse<Unit>

    // 관리자 - 파티모집별 지원자 조회
    suspend fun getRecruitmentApplicants(partyId: Int, partyRecruitmentId: Int, page: Int, limit: Int, sort: String, order: String): ApiResponse<RecruitmentApplicantDto>

    // 파티장이 지원자 수락
    suspend fun acceptApplicant(partyId: Int, partyApplicationId: Int): ApiResponse<ApprovalAndRejectionDto>

    // 파티장이 지원자 거절
    suspend fun rejectApplicant(partyId: Int, partyApplicationId: Int): ApiResponse<ApprovalAndRejectionDto>

    // 지원한 모집공고 취소하기(검토중일때)
    suspend fun cancelRecruitment(partyId: Int, partyApplicationId: Int): ApiResponse<Unit>

    // 모집공고에 유저가 지원했는지 여부
    suspend fun checkUserApplicationStatus(partyId: Int, partyRecruitmentId: Int): ApiResponse<CheckUserApplicationStatusDto>

    // 파티 나가기 (파티유저가)
    suspend fun exitParty(partyId: Int): ApiResponse<Unit>

    // 파티 모집 완료 처리
    suspend fun completePartyRecruitment(partyId: Int, partyRecruitmentId: Int): ApiResponse<PartyRecruitmentCompletedDto>

    // 유저가 파티 합류 최종 수락
    suspend fun approvalParty(partyId: Int, partyApplicationId: Int): ApiResponse<ApprovalAndRejectionDto>

    // 유저가 파티 합류 최종 거절
    suspend fun rejectionParty(partyId: Int, partyApplicationId: Int): ApiResponse<ApprovalAndRejectionDto>
}