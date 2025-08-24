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
import com.party.data.dto.party.PartyStatusDto
import com.party.data.dto.party.PartyUsersDto
import com.party.data.dto.party.PersonalRecruitmentListDto
import com.party.data.dto.party.RecruitmentApplicantDto
import com.party.data.dto.party.RecruitmentCreateDto
import com.party.data.dto.party.RecruitmentDetailDto
import com.party.data.dto.party.RecruitmentListDto
import com.party.data.service.PartyService
import com.party.domain.model.party.DelegatePartyMasterRequest
import com.party.domain.model.party.ModifyPartyStatusRequest
import com.party.domain.model.party.ModifyPartyUserPositionRequest
import com.party.domain.model.party.ModifyRecruitmentRequest
import com.party.domain.model.party.PartyApplyRequest
import com.party.domain.model.party.RecruitmentCreateRequest
import com.skydoves.sandwich.ApiResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class PartyRemoteSourceImpl @Inject constructor(
    private val partyService: PartyService,
): PartyRemoteSource{
    override suspend fun getPersonalizedParties(
        page: Int,
        size: Int,
        sort: String,
        order: String
    ): ApiResponse<PersonalRecruitmentListDto> {
        return partyService.getPersonalizedParties(page = page, size = size, sort = sort, order = order)
    }

    override suspend fun getRecruitmentList(
        page: Int,
        size: Int,
        sort: String,
        order: String,
        titleSearch: String?,
        partyTypes: List<Int>,
        position: List<Int>,
    ): ApiResponse<RecruitmentListDto> {
        return partyService.getRecruitmentList(page = page, size = size, sort = sort, order = order, titleSearch = titleSearch, partyTypes = partyTypes, position = position)
    }

    override suspend fun getPartyList(
        page: Int,
        size: Int,
        sort: String,
        order: String,
        partyTypes: List<Int>,
        titleSearch: String?,
        status: String?
    ): ApiResponse<PartyListDto> {
        return partyService.getPartyList(page = page, size = size, sort = sort, order = order, partyTypes = partyTypes, titleSearch = titleSearch, status = status)
    }

    override suspend fun getRecruitmentDetail(partyRecruitmentId: Int): ApiResponse<RecruitmentDetailDto> {
        return partyService.getRecruitmentDetail(partyRecruitmentId = partyRecruitmentId)
    }

    override suspend fun getPartyDetail(partyId: Int): ApiResponse<PartyDetailDto> {
        return partyService.getPartyDetail(partyId = partyId)
    }

    override suspend fun getPartyUsers(
        partyId: Int,
        page: Int,
        limit: Int,
        sort: String,
        order: String
    ): ApiResponse<PartyUsersDto> {
        return partyService.getPartyUsers(partyId = partyId, page = page, limit = limit, sort = sort, order = order)
    }

    override suspend fun getPartyRecruitmentList(
        partyId: Int,
        sort: String,
        order: String,
        main: String?,
        status: String?,
    ): ApiResponse<List<PartyRecruitmentDto>> {
        return partyService.getPartyRecruitmentList(partyId = partyId, sort = sort, order = order, main = main, status = status)
    }

    override suspend fun getPartyAuthority(partyId: Int): ApiResponse<PartyAuthorityDto> {
        return partyService.getPartyAuthority(partyId = partyId)
    }

    override suspend fun createParty(
        title: RequestBody,
        content: RequestBody,
        partyTypeId: RequestBody,
        positionId: RequestBody,
        image: MultipartBody.Part
    ): ApiResponse<PartyCreateDto> {
        return partyService.saveParty(
            title = title,
            content = content,
            partyTypeId = partyTypeId,
            positionId = positionId,
            image = image
        )
    }

    override suspend fun modifyParty(
        partyId: Int,
        title: RequestBody?,
        content: RequestBody?,
        partyTypeId: RequestBody?,
        image: MultipartBody.Part?,
    ): ApiResponse<PartyModifyDto> {
        return partyService.modifyParty(
            partyId = partyId,
            title = title,
            content = content,
            partyTypeId = partyTypeId,
            image = image,
        )
    }

    override suspend fun modifyPartyStatus(
        partyId: Int,
        modifyPartyStatusRequest: ModifyPartyStatusRequest
    ): ApiResponse<PartyStatusDto> {
        return partyService.modifyPartyStatus(
            partyId = partyId,
            modifyPartyStatusRequest = modifyPartyStatusRequest
        )
    }

    override suspend fun applyPartyRecruitment(
        partyRecruitmentId: Int,
        partyId: Int,
        partyApplyRequest: PartyApplyRequest
    ): ApiResponse<PartyApplyDto> {
        return partyService.applyPartyRecruitment(partyId = partyId, partyRecruitmentId = partyRecruitmentId, partyApplyRequest = partyApplyRequest)
    }

    override suspend fun createRecruitment(
        partyId: Int,
        recruitmentCreateRequest: RecruitmentCreateRequest
    ): ApiResponse<RecruitmentCreateDto> {
        return partyService.saveRecruitment(partyId = partyId, recruitmentCreateRequest = recruitmentCreateRequest)
    }

    override suspend fun getPartyMembers(
        partyId: Int,
        page: Int,
        limit: Int,
        sort: String,
        order: String,
        main: String?,
        nickname: String?
    ): ApiResponse<PartyMembersInfoDto> {
        return partyService.getPartyMembers(partyId = partyId, page = page, limit = limit, sort = sort, order = order, main = main, nickname = nickname)
    }

    override suspend fun modifyPartyMemberPosition(
        partyId: Int,
        partyUserId: Int,
        modifyPartyUserPositionRequest: ModifyPartyUserPositionRequest
    ): ApiResponse<Unit> {
        return partyService.modifyPartyMemberPosition(partyId = partyId, partyUserId = partyUserId, modifyPartyUserPositionRequest = modifyPartyUserPositionRequest)
    }

    override suspend fun deleteParty(partyId: Int): ApiResponse<Unit> {
        return partyService.deleteParty(partyId = partyId)
    }

    override suspend fun deletePartyMember(partyId: Int, partyUserId: Int): ApiResponse<Unit> {
        return partyService.deletePartyMember(partyId = partyId, partyUserId = partyUserId)
    }

    override suspend fun changeMaster(
        partyId: Int,
        delegatePartyMasterRequest: DelegatePartyMasterRequest
    ): ApiResponse<Unit> {
        return partyService.changeMaster(partyId = partyId, delegatePartyMasterRequest = delegatePartyMasterRequest)
    }

    override suspend fun deleteRecruitment(
        partyId: Int,
        partyRecruitmentId: Int
    ): ApiResponse<Unit> {
        return partyService.deleteRecruitment(partyId = partyId, partyRecruitmentId = partyRecruitmentId)
    }

    override suspend fun modifyRecruitment(
        partyId: Int,
        partyRecruitmentId: Int,
        modifyRecruitmentRequest: ModifyRecruitmentRequest
    ): ApiResponse<Unit> {
        return partyService.modifyRecruitment(partyId = partyId, partyRecruitmentId = partyRecruitmentId, modifyRecruitmentRequest = modifyRecruitmentRequest)
    }

    override suspend fun getRecruitmentApplicants(
        partyId: Int,
        partyRecruitmentId: Int,
        page: Int,
        limit: Int,
        sort: String,
        order: String
    ): ApiResponse<RecruitmentApplicantDto> {
        return partyService.getRecruitmentApplicants(partyId = partyId, partyRecruitmentId = partyRecruitmentId, page = page, limit = limit, sort = sort, order = order)
    }

    override suspend fun acceptApplicant(
        partyId: Int,
        partyApplicationId: Int
    ): ApiResponse<ApprovalAndRejectionDto> {
        return partyService.acceptApplicant(partyId = partyId, partyApplicationId = partyApplicationId)
    }

    override suspend fun rejectApplicant(
        partyId: Int,
        partyApplicationId: Int
    ): ApiResponse<ApprovalAndRejectionDto> {
        return partyService.rejectApplicant(partyId = partyId, partyApplicationId = partyApplicationId)
    }

    override suspend fun cancelRecruitment(
        partyId: Int,
        partyApplicationId: Int
    ): ApiResponse<Unit> {
        return partyService.cancelRecruitment(partyId = partyId, partyApplicationId = partyApplicationId)
    }

    override suspend fun checkUserApplicationStatus(
        partyId: Int,
        partyRecruitmentId: Int
    ): ApiResponse<CheckUserApplicationStatusDto> {
        return partyService.checkUserApplicationStatus(partyId = partyId, partyRecruitmentId = partyRecruitmentId)
    }

    override suspend fun exitParty(partyId: Int): ApiResponse<Unit> {
        return partyService.exitParty(partyId = partyId)
    }

    override suspend fun completePartyRecruitment(
        partyId: Int,
        partyRecruitmentId: Int
    ): ApiResponse<PartyRecruitmentCompletedDto> {
        return partyService.completePartyRecruitment(partyId = partyId, partyRecruitmentId = partyRecruitmentId)
    }

    override suspend fun approvalParty(
        partyId: Int,
        partyApplicationId: Int
    ): ApiResponse<ApprovalAndRejectionDto> {
        return partyService.approvalParty(partyId = partyId, partyApplicationId = partyApplicationId)
    }

    override suspend fun rejectionParty(
        partyId: Int,
        partyApplicationId: Int
    ): ApiResponse<ApprovalAndRejectionDto> {
        return partyService.rejectionParty(partyApplicationId = partyApplicationId, partyId = partyId)
    }

}