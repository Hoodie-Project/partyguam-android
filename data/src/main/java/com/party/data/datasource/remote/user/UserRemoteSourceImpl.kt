package com.party.data.datasource.remote.user

import com.party.data.dto.user.ReportsDto
import com.party.data.dto.user.auth.LinkGoogleDto
import com.party.data.dto.user.auth.LinkKakaoDto
import com.party.data.dto.user.auth.MySocialOauthDto
import com.party.data.dto.user.auth.SocialLoginDto
import com.party.data.dto.user.auth.UserSignUpDto
import com.party.data.dto.user.detail.LocationDto
import com.party.data.dto.user.detail.ModifyCarrierDto
import com.party.data.dto.user.detail.PersonalityListDto
import com.party.data.dto.user.detail.PersonalitySaveDto
import com.party.data.dto.user.detail.PositionListDto
import com.party.data.dto.user.detail.SaveCarrierDto
import com.party.data.dto.user.detail.SaveInterestLocationDto
import com.party.data.dto.user.party.MyPartyDto
import com.party.data.dto.user.profile.UserProfileDto
import com.party.data.dto.user.recruitment.MyRecruitmentDto
import com.party.data.service.NoTokenService
import com.party.data.service.UserService
import com.party.domain.model.user.AccessTokenRequest
import com.party.domain.model.user.LinkKakaoRequest
import com.party.domain.model.user.ReportsRequest
import com.party.domain.model.user.detail.InterestLocationList
import com.party.domain.model.user.detail.ModifyCarrierList
import com.party.domain.model.user.detail.PersonalitySaveRequest
import com.party.domain.model.user.detail.SaveCarrierList
import com.party.domain.model.user.profile.UserProfileModifyDto
import com.party.domain.model.user.signup.UserSignUpRequest
import com.skydoves.sandwich.ApiResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class UserRemoteSourceImpl @Inject constructor(
    private val noTokenService: NoTokenService,
    private val userService: UserService,
): UserRemoteSource{
    override suspend fun googleLogin(accessTokenRequest: AccessTokenRequest): ApiResponse<SocialLoginDto> {
        return noTokenService.loginGoogle(accessTokenRequest = accessTokenRequest)
    }

    override suspend fun kakaoLogin(accessToken: String): ApiResponse<SocialLoginDto> {
        return noTokenService.loginKakao(accessToken = accessToken)
    }

    override suspend fun getMySocialOauth(): ApiResponse<List<MySocialOauthDto>> {
        return userService.getMySocialOauth()
    }

    override suspend fun linkKakao(linkKakaoRequest: LinkKakaoRequest): ApiResponse<LinkKakaoDto> {
        return userService.linkKakao(linkKakaoRequest = linkKakaoRequest)
    }

    override suspend fun linkGoogle(accessTokenRequest: AccessTokenRequest): ApiResponse<LinkGoogleDto> {
        return userService.linkGoogle(accessTokenRequest = accessTokenRequest)
    }

    override suspend fun checkNickName(
        signupAccessToken: String,
        nickname: String
    ): ApiResponse<String> {
        return noTokenService.checkNickName(signupAccessToken = signupAccessToken, nickname = nickname)
    }

    override suspend fun userSignUp(
        signupAccessToken: String,
        userSignUpRequest: UserSignUpRequest
    ): ApiResponse<UserSignUpDto> {
        return noTokenService.userSignUp(signupAccessToken = signupAccessToken, userSignUpRequest = userSignUpRequest)
    }

    override suspend fun getLocations(province: String): ApiResponse<List<LocationDto>> {
        return userService.getLocations(province = province)
    }

    override suspend fun saveInterestLocation(
        locations: InterestLocationList,
    ): ApiResponse<List<SaveInterestLocationDto>> {
        return userService.saveInterestLocation(locations = locations)
    }

    override suspend fun getPositions(
        main: String
    ): ApiResponse<List<PositionListDto>> {
        return userService.getPositions(main = main)
    }

    override suspend fun saveCarrier(
        career: SaveCarrierList
    ): ApiResponse<SaveCarrierDto> {
        return userService.saveCareer(career = career)
    }

    override suspend fun modifyCarrier(career: ModifyCarrierList): ApiResponse<ModifyCarrierDto> {
        return userService.modifyCareer(career = career)
    }

    override suspend fun deleteCareer(): ApiResponse<Unit> {
        return userService.deleteCareer()
    }

    override suspend fun getPersonalities(): ApiResponse<List<PersonalityListDto>> {
        return userService.getPersonalities()
    }

    override suspend fun savePersonalities(
        personalitySaveRequest: PersonalitySaveRequest
    ): ApiResponse<List<PersonalitySaveDto>> {
        return userService.savePersonalities(personalitySaveRequest = personalitySaveRequest)
    }

    override suspend fun getMyParties(
        page: Int,
        limit: Int,
        sort: String,
        order: String
    ): ApiResponse<MyPartyDto> {
        return userService.getMyParties(page = page, limit = limit, sort = sort, order = order)
    }

    override suspend fun getMyRecruitments(
        page: Int,
        limit: Int,
        sort: String,
        order: String
    ): ApiResponse<MyRecruitmentDto> {
        return userService.getMyRecruitments(page = page, limit = limit, sort = sort, order = order)
    }

    override suspend fun getUserProfile(): ApiResponse<UserProfileDto> {
        return userService.getUserProfile()
    }

    override suspend fun updateUserProfile(
        image: MultipartBody.Part?,
        genderVisible: Boolean?,
        birthVisible: Boolean?,
        portfolioTitle: RequestBody?,
        portfolio: RequestBody?,
    ): ApiResponse<UserProfileModifyDto> {
        return userService.updateUserProfile(
            image = image,
            genderVisible = genderVisible,
            birthVisible = birthVisible,
            portfolioTitle = portfolioTitle,
            portfolio = portfolio
        )
    }

    override suspend fun deletePersonalities(personalityQuestionId: Int): ApiResponse<Unit> {
        return userService.deletePersonalities(personalityQuestionId = personalityQuestionId)
    }

    override suspend fun deleteInterestLocation(): ApiResponse<Unit> {
        return userService.deleteInterestLocations()
    }

    override suspend fun logout(): ApiResponse<Unit> {
        return userService.logout()
    }

    override suspend fun signOut(): ApiResponse<Unit> {
        return userService.signOut()
    }

    override suspend fun reports(reportsRequest: ReportsRequest): ApiResponse<ReportsDto> {
        return userService.reports(reportsRequest = reportsRequest)
    }
}