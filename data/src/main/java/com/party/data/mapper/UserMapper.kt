package com.party.data.mapper

import com.party.data.dto.user.notification.NotificationDataDto
import com.party.data.dto.user.notification.NotificationDto
import com.party.data.dto.user.notification.NotificationTypeDto
import com.party.data.dto.user.ReportsDto
import com.party.data.dto.user.auth.LinkGoogleDto
import com.party.data.dto.user.auth.LinkKakaoDto
import com.party.data.dto.user.auth.MySocialOauthDto
import com.party.data.dto.user.auth.SocialLoginErrorDto
import com.party.data.dto.user.auth.SocialLoginSuccessDto
import com.party.data.dto.user.auth.UserSignUpDto
import com.party.data.dto.user.detail.LocationDto
import com.party.data.dto.user.detail.ModifyCarrierItem
import com.party.data.dto.user.detail.PersonalityListDto
import com.party.data.dto.user.detail.PersonalitySaveDto
import com.party.data.dto.user.detail.PositionListDto
import com.party.data.dto.user.detail.SaveInterestLocationDto
import com.party.data.dto.user.detail.UserLikeLocationDto
import com.party.data.dto.user.notification.ReadNotificationDto
import com.party.data.dto.user.party.MyPartyDto
import com.party.data.dto.user.party.PartyUserDto
import com.party.data.dto.user.profile.PersonalityOptionDto
import com.party.data.dto.user.profile.PersonalityQuestionDto
import com.party.data.dto.user.profile.UserCareerDto
import com.party.data.dto.user.profile.UserLocationDto
import com.party.data.dto.user.profile.UserPersonalityDto
import com.party.data.dto.user.profile.UserProfileDto
import com.party.data.dto.user.profile.UserProfileLocationDto
import com.party.data.dto.user.profile.UserProfilePositionDto
import com.party.data.dto.user.recruitment.MyRecruitmentDto
import com.party.data.dto.user.recruitment.PartyApplicationDto
import com.party.data.util.convertToImageUrl
import com.party.domain.model.user.LinkGoogle
import com.party.domain.model.user.LinkKakao
import com.party.domain.model.user.MySocialOauth
import com.party.domain.model.user.notification.Notification
import com.party.domain.model.user.notification.NotificationData
import com.party.domain.model.user.notification.NotificationType
import com.party.domain.model.user.Reports
import com.party.domain.model.user.SocialLogin
import com.party.domain.model.user.SocialLoginError
import com.party.domain.model.user.SocialLoginSuccess
import com.party.domain.model.user.detail.Location
import com.party.domain.model.user.detail.ModifyCarrier
import com.party.domain.model.user.detail.PersonalityList
import com.party.domain.model.user.detail.PersonalityListOption
import com.party.domain.model.user.detail.PersonalitySave
import com.party.domain.model.user.detail.PositionList
import com.party.domain.model.user.detail.SaveCarrier
import com.party.domain.model.user.detail.SaveInterestLocation
import com.party.domain.model.user.detail.UserLikeLocation
import com.party.domain.model.user.notification.ReadNotification
import com.party.domain.model.user.party.MyParty
import com.party.domain.model.user.party.Party
import com.party.domain.model.user.party.PartyType
import com.party.domain.model.user.party.PartyUser
import com.party.domain.model.user.party.Position
import com.party.domain.model.user.profile.PersonalityOption
import com.party.domain.model.user.profile.PersonalityQuestion
import com.party.domain.model.user.profile.UserCareer
import com.party.domain.model.user.profile.UserLocation
import com.party.domain.model.user.profile.UserPersonality
import com.party.domain.model.user.profile.UserProfile
import com.party.domain.model.user.profile.UserProfileLocation
import com.party.domain.model.user.profile.UserProfileModify
import com.party.domain.model.user.profile.UserProfileModifyDto
import com.party.domain.model.user.profile.UserProfilePosition
import com.party.domain.model.user.recruitment.MyRecruitment
import com.party.domain.model.user.recruitment.PartyApplication
import com.party.domain.model.user.recruitment.PartyRecruitment
import com.party.domain.model.user.signup.UserSignUp

object UserMapper {
    fun mapperToSocialLoginResponse(socialLoginEntity: SocialLoginSuccessDto): SocialLogin {
        return SocialLoginSuccess(
            accessToken = socialLoginEntity.accessToken,
            refreshToken = socialLoginEntity.refreshToken
        )
    }

    fun mapperSocialLoginErrorResponse(socialLoginErrorEntity: SocialLoginErrorDto): SocialLoginError {
        return SocialLoginError(
            message = socialLoginErrorEntity.message,
            signupAccessToken = socialLoginErrorEntity.signupAccessToken
        )
    }

    fun mapperUserSignUpResponse(userSignUpEntity: UserSignUpDto): UserSignUp {
        return UserSignUp(
            accessToken = userSignUpEntity.accessToken
        )
    }

    fun mapperToLocationResponse(locationEntity: LocationDto): Location {
        return Location(
            id = locationEntity.id,
            province = locationEntity.province,
            city = locationEntity.city,
        )
    }

    fun mapperToSaveInterestLocationResponse(saveInterestLocationEntity: SaveInterestLocationDto): SaveInterestLocation {
        return SaveInterestLocation(
            id = saveInterestLocationEntity.id,
            userId = saveInterestLocationEntity.userId,
            locationId = saveInterestLocationEntity.locationId,
        )
    }

    fun mapperToPositionListResponse(positionListEntity: PositionListDto): PositionList {
        return PositionList(
            id = positionListEntity.id,
            main = positionListEntity.main,
            sub = positionListEntity.sub,
        )
    }

    fun mapperToUserLikeLocation(userLikeLocationDto: UserLikeLocationDto): UserLikeLocation{
        return UserLikeLocation(
            id = userLikeLocationDto.id,
            userId = userLikeLocationDto.userId,
            locationId = userLikeLocationDto.locationId
        )
    }

    /*fun mapperToSaveCarrierResponse(saveCarrierEntity: SaveCarrierItem): SaveCarrier {
        return SaveCarrier(
            id = saveCarrierEntity.id,
            positionId = saveCarrierEntity.positionId,
            years = saveCarrierEntity.years,
            careerType = saveCarrierEntity.careerType,
        )
    }*/

    fun mapperToModifyCarrierResponse(modifyCarrierItem: ModifyCarrierItem): ModifyCarrier{
        return ModifyCarrier(
            positionId = modifyCarrierItem.positionId,
            years = modifyCarrierItem.years,
            careerType = modifyCarrierItem.careerType,
        )
    }

    fun mapperToPersonalityListResponse(personalityListEntity: PersonalityListDto): PersonalityList {
        return PersonalityList(
            id = personalityListEntity.id,
            content = personalityListEntity.content,
            responseCount = personalityListEntity.responseCount,
            personalityOptions = personalityListEntity.personalityOptions.map {
                PersonalityListOption(
                    id = it.id,
                    personalityQuestionId = it.personalityQuestionId,
                    content = it.content
                )
            }
        )
    }

    fun mapperToPersonalitySaveResponse(personalitySaveEntity: PersonalitySaveDto): PersonalitySave {
        return PersonalitySave(
            id = personalitySaveEntity.id,
            userId = personalitySaveEntity.userId,
            personalityOptionId = personalitySaveEntity.personalityOptionId,
        )
    }

    // 내 파티 리스트 조회
    fun mapperToMyParty(myPartyDto: MyPartyDto): MyParty{
        return MyParty(
            total = myPartyDto.total,
            partyUsers = myPartyDto.partyUsers.map { mapperToPartyUser(it) }
        )
    }

    private fun mapperToPartyUser(partyUserDto: PartyUserDto): PartyUser{
        return PartyUser(
            id = partyUserDto.id,
            createdAt = partyUserDto.createdAt,
            position = Position(
                main = partyUserDto.position.main,
                sub = partyUserDto.position.sub
            ),
            party = Party(
                id = partyUserDto.party.id,
                title = partyUserDto.party.title,
                image = convertToImageUrl(partyUserDto.party.image),
                status = partyUserDto.party.status,
                partyType = PartyType(
                    type = partyUserDto.party.partyType.type
                )
            )
        )
    }

    // 내 지원목록 리스트 조회
    fun mapperToMyRecruitment(myRecruitmentDto: MyRecruitmentDto): MyRecruitment {
        return MyRecruitment(
            total = myRecruitmentDto.total,
            partyApplications = myRecruitmentDto.partyApplications.map { mapperToPartyApplication(it) }
        )
    }

    private fun mapperToPartyApplication(partyApplicationDto: PartyApplicationDto): PartyApplication {
        return PartyApplication(
            id = partyApplicationDto.id,
            message = partyApplicationDto.message,
            status = partyApplicationDto.status,
            createdAt = partyApplicationDto.createdAt,
            partyRecruitment = PartyRecruitment(
                id = partyApplicationDto.id,
                status = partyApplicationDto.status,
                position = com.party.domain.model.user.recruitment.Position(
                    main = partyApplicationDto.partyRecruitment.position.main,
                    sub = partyApplicationDto.partyRecruitment.position.sub
                ),
                party = com.party.domain.model.user.recruitment.Party(
                    id = partyApplicationDto.partyRecruitment.party.id,
                    title = partyApplicationDto.partyRecruitment.party.title,
                    image = convertToImageUrl(partyApplicationDto.partyRecruitment.party.image),
                    partyType = com.party.domain.model.user.recruitment.PartyType(
                        type = partyApplicationDto.partyRecruitment.party.partyType.type
                    )
                )
            )
        )
    }

    fun mapperToUserProfile(userProfileDto: UserProfileDto): UserProfile{
        return UserProfile(
            nickname = userProfileDto.nickname,
            birth = userProfileDto.birth,
            birthVisible = userProfileDto.birthVisible,
            gender = userProfileDto.gender,
            genderVisible = userProfileDto.genderVisible,
            portfolioTitle = userProfileDto.portfolioTitle,
            portfolio = userProfileDto.portfolio,
            image = convertToImageUrl(userProfileDto.image),
            createdAt = userProfileDto.createdAt,
            updatedAt = userProfileDto.updatedAt,
            userPersonalities = userProfileDto.userPersonalities.map { mapperToUserPersonality(it) },
            userCareers = userProfileDto.userCareers.map { mapperToUserCareer(it) },
            userLocations = userProfileDto.userLocations.map { mapperToUserLocation(it) }
        )
    }

    private fun mapperToUserPersonality(userPersonalityDto: UserPersonalityDto): UserPersonality {
        return UserPersonality(
            id = userPersonalityDto.id,
            personalityOption = mapperToPersonalityOption(userPersonalityDto.personalityOption)
        )
    }

    private fun mapperToPersonalityOption(personalityOptionDto: PersonalityOptionDto): PersonalityOption {
        return PersonalityOption(
            id = personalityOptionDto.id,
            content = personalityOptionDto.content,
            personalityQuestion = mapperToPersonalityQuestion(personalityOptionDto.personalityQuestion)
        )
    }

    private fun mapperToPersonalityQuestion(personalityQuestionDto: PersonalityQuestionDto): PersonalityQuestion {
        return PersonalityQuestion(
            id = personalityQuestionDto.id,
            content = personalityQuestionDto.content,
            responseCount = personalityQuestionDto.responseCount
        )
    }

    private fun mapperToUserCareer(userCareerDto: UserCareerDto): UserCareer {
        return UserCareer(
            id = userCareerDto.id,
            years = userCareerDto.years,
            careerType = userCareerDto.careerType,
            position = mapperToUserProfilePosition(userCareerDto.position)
        )
    }

    private fun mapperToUserProfilePosition(userProfilePositionDto: UserProfilePositionDto): UserProfilePosition {
        return UserProfilePosition(
            main = userProfilePositionDto.main,
            sub = userProfilePositionDto.sub
        )
    }

    private fun mapperToUserLocation(userLocationDto: UserLocationDto): UserLocation {
        return UserLocation(
            id = userLocationDto.id,
            location = mapperToUserProfileLocation(userLocationDto.location)
        )
    }

    private fun mapperToUserProfileLocation(userProfileLocationDto: UserProfileLocationDto): UserProfileLocation {
        return UserProfileLocation(
            id = userProfileLocationDto.id,
            province = userProfileLocationDto.province,
            city = userProfileLocationDto.city
        )
    }

    fun mapperToUserProfileModify(userProfileModifyDto: UserProfileModifyDto): UserProfileModify{
        return UserProfileModify(
            nickname = userProfileModifyDto.nickname,
            birth = userProfileModifyDto.birth,
            birthVisible = userProfileModifyDto.birthVisible,
            gender = userProfileModifyDto.gender,
            genderVisible = userProfileModifyDto.genderVisible,
            portfolioTitle = userProfileModifyDto.portfolioTitle,
            portfolio = userProfileModifyDto.portfolio,
            image = convertToImageUrl(userProfileModifyDto.image),
            createdAt = userProfileModifyDto.createdAt,
            updatedAt = userProfileModifyDto.updatedAt,
        )
    }

    fun mapperToLinkKakao(linkKakaoDto: LinkKakaoDto): LinkKakao{
        return LinkKakao(
            message = linkKakaoDto.message
        )
    }

    fun mapperToLinkGoogle(linkGoogleDto: LinkGoogleDto): LinkGoogle{
        return LinkGoogle(
            message = linkGoogleDto.message
        )
    }

    fun mapperToMySocialOauth(mySocialOauthDto: MySocialOauthDto): MySocialOauth{
        return MySocialOauth(
            provider = mySocialOauthDto.provider,
        )
    }

    fun mapperToReports(reportsDto: ReportsDto): Reports{
        return Reports(
            id = reportsDto.id,
            type = reportsDto.type,
            typeId = reportsDto.typeId,
            content = reportsDto.content,
        )
    }

    fun mapperToNotification(notificationDto: NotificationDto): Notification {
        return Notification(
            nextCursor = notificationDto.nextCursor,
            notifications = notificationDto.notifications.map {
                mapperToNotificationData(it)
            }
        )
    }

    private fun mapperToNotificationData(notificationDataDto: NotificationDataDto): NotificationData {
        return NotificationData(
            id = notificationDataDto.id,
            notificationType = mapperToNotificationType(notificationDataDto.notificationType),
            title = notificationDataDto.title,
            link = notificationDataDto.link,
            message = notificationDataDto.message,
            isRead = notificationDataDto.isRead,
            createdAt = notificationDataDto.createdAt,
            image = convertToImageUrl(notificationDataDto.image)
        )
    }

    private fun mapperToNotificationType(notificationTypeDto: NotificationTypeDto): NotificationType {
        return NotificationType(
            type = notificationTypeDto.type,
            label = notificationTypeDto.label,
        )
    }

    fun mapperToReadNotification(readNotificationDto: ReadNotificationDto): ReadNotification{
        return ReadNotification(
            message = readNotificationDto.message
        )
    }
}