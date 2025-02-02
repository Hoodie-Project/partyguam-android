package com.party.domain.repository

import com.party.common.ServerApiResponse
import com.party.domain.model.party.ApprovalAndRejection
import com.party.domain.model.party.CheckUserApplicationStatus
import com.party.domain.model.party.DelegatePartyMasterRequest
import com.party.domain.model.party.ModifyPartyUserPositionRequest
import com.party.domain.model.party.ModifyRecruitmentRequest
import com.party.domain.model.party.PartyApply
import com.party.domain.model.party.PartyApplyRequest
import com.party.domain.model.party.PartyCreate
import com.party.domain.model.party.PartyDetail
import com.party.domain.model.party.PartyList
import com.party.domain.model.party.PartyMembersInfo
import com.party.domain.model.party.PartyModify
import com.party.domain.model.party.PartyRecruitment
import com.party.domain.model.party.PartyUsers
import com.party.domain.model.party.PersonalRecruitmentList
import com.party.domain.model.party.RecruitmentApplicant
import com.party.domain.model.party.RecruitmentCreate
import com.party.domain.model.party.RecruitmentCreateRequest
import com.party.domain.model.party.RecruitmentDetail
import com.party.domain.model.party.RecruitmentList
import com.party.domain.model.user.PartyAuthority
import okhttp3.MultipartBody
import okhttp3.RequestBody

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
        order: String,
        titleSearch: String?,
        partyTypes: List<Int>,
        position: List<Int>
    ): ServerApiResponse<RecruitmentList>

    // 파티 리스트 조회
    suspend fun getPartyList(
        page: Int,
        size: Int,
        sort: String,
        order: String,
        partyTypes: List<Int>,
        titleSearch: String?,
        status: String?
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
    suspend fun getPartyRecruitmentList(partyId: Int, sort: String, order: String, main: String?, status: String): ServerApiResponse<List<PartyRecruitment>>

    // 파티 상세 조회 - 나의 파티 권한 조회
    suspend fun getPartyAuthority(partyId: Int): ServerApiResponse<PartyAuthority>

    // 파티 생성
    suspend fun saveParty(
        title: RequestBody,
        content: RequestBody,
        partyTypeId: RequestBody,
        positionId: RequestBody,
        image: MultipartBody.Part
    ): ServerApiResponse<PartyCreate>

    // 파티 수정
    suspend fun modifyParty(
        partyId: Int,
        title: RequestBody?,
        content: RequestBody?,
        partyTypeId: RequestBody?,
        image: MultipartBody.Part?,
        status: RequestBody?
    ): ServerApiResponse<PartyModify>

    // 파티지원하기 - 모집공고 지원
    suspend fun applyPartyRecruitment(
        partyId: Int,
        partyRecruitmentId: Int,
        partyApplyRequest: PartyApplyRequest,
    ): ServerApiResponse<PartyApply>

    // 모집공고 추가하기
    suspend fun saveRecruitment(
        partyId: Int,
        recruitmentCreateRequest: RecruitmentCreateRequest
    ): ServerApiResponse<RecruitmentCreate>

    // 관리자 - 파티원 리스트 조회
    suspend fun getPartyMembers(partyId: Int, page: Int, limit: Int, sort: String, order: String, main: String?, nickname: String?): ServerApiResponse<PartyMembersInfo>

    // 관리자 - 파티원 포지션 변경
    suspend fun modifyPartyMemberPosition(partyId: Int, partyUserId: Int, modifyPartyUserPositionRequest: ModifyPartyUserPositionRequest): ServerApiResponse<Unit>

    // 관리자 - 파티 삭제
    suspend fun deleteParty(partyId: Int): ServerApiResponse<Unit>

    // 관리자 - 파티원 내보내기
    suspend fun deletePartyMember(partyId: Int, partyUserId: Int): ServerApiResponse<Unit>

    // 관리자 - 파티장 위임하기
    suspend fun changeMaster(partyId: Int, delegatePartyMasterRequest: DelegatePartyMasterRequest): ServerApiResponse<Unit>

    // 관리자 - 파티모집 삭제
    suspend fun deleteRecruitment(partyId: Int, partyRecruitmentId: Int): ServerApiResponse<Unit>

    // 관리자 - 파티모집 수정
    suspend fun modifyRecruitment(partyId: Int, partyRecruitmentId: Int, modifyRecruitmentRequest: ModifyRecruitmentRequest): ServerApiResponse<Unit>

    // 관리자 - 파티모집별 지원자 조회
    suspend fun getRecruitmentApplicants(partyId: Int, partyRecruitmentId: Int, page: Int, limit: Int, sort: String, order: String): ServerApiResponse<RecruitmentApplicant>

    // 파티장이 지원자 수락
    suspend fun acceptApplicant(partyId: Int, partyApplicationId: Int): ServerApiResponse<ApprovalAndRejection>

    // 파티장이 지원자 거절
    suspend fun rejectApplicant(partyId: Int, partyApplicationId: Int): ServerApiResponse<ApprovalAndRejection>

    // 지원한 모집공고 취소하기(검토중일때)
    suspend fun cancelRecruitment(partyId: Int, partyApplicationId: Int): ServerApiResponse<Unit>

    // 모집공고에 유저가 지원했는지 여부
    suspend fun checkUserApplicationStatus(partyId: Int, partyRecruitmentId: Int): ServerApiResponse<CheckUserApplicationStatus>

    // 파티 나가기 (파티유저가)
    suspend fun exitParty(partyId: Int): ServerApiResponse<Unit>
}
