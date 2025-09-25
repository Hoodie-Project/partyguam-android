package com.party.common

import kotlinx.serialization.Serializable

sealed interface Screens {
    @Serializable
    data object Splash: Screens
    @Serializable
    data object GuidePermission: Screens
    @Serializable
    data object Login: Screens
    @Serializable
    data class Join(
        val email: String,
        val signupAccessToken: String
    ): Screens
    @Serializable
    data object JoinEmail: Screens
    @Serializable
    data object JoinNickname: Screens
    @Serializable
    data object JoinBirthDay: Screens
    @Serializable
    data object JoinGender: Screens
    @Serializable
    data object JoinComplete: Screens
    @Serializable
    data class DetailProfile(val userNickName: String): Screens
    @Serializable
    data class DetailCarrier(val userNickName: String): Screens
    @Serializable
    data class ChoiceCarrierPosition(val isMain: Boolean): Screens
    @Serializable
    data object SelectTendency1: Screens
    @Serializable
    data object SelectTendency2: Screens
    @Serializable
    data object SelectTendency3: Screens
    @Serializable
    data object SelectTendency4: Screens
    @Serializable
    data object SelectTendencyComplete: Screens
    @Serializable
    data object Home: Screens
    @Serializable
    data object State: Screens
    @Serializable
    data object Profile: Screens
    @Serializable
    data class RecruitmentDetail(val partyRecruitmentId: Int, val partyId: Int): Screens
    @Serializable
    data class PartyApply(val partyId: Int, val partyRecruitmentId: Int): Screens
    @Serializable
    data class PartyDetail(val partyId: Int): Screens
    @Serializable
    data object PartyCreate: Screens
    @Serializable
    data class RecruitmentCreate(val partyId: Int): Screens
    @Serializable
    data object Search: Screens
    @Serializable
    data class PartyEdit(val partyId: Int): Screens
    @Serializable
    data class PartyUserManage(val partyId: Int): Screens
    @Serializable
    data class PartyEditRecruitment(val partyId: Int): Screens
    @Serializable
    data class ManageApplicant(val partyId: Int): Screens
    @Serializable
    data class RecruitmentEdit(val partyRecruitmentId: Int, val partyId: Int): Screens
    @Serializable
    data object ManageAuth: Screens
    @Serializable
    data object UserDelete: Screens
    @Serializable
    data object ProfileEdit: Screens
    @Serializable
    data object ProfileEditCareer: Screens
    @Serializable
    data object ProfileEditPortfolio: Screens
    @Serializable
    data object ProfileEditTime: Screens
    @Serializable
    data object ProfileEditLocation: Screens
    @Serializable
    data object ProfileEditTendency: Screens
    @Serializable
    data object CustomerInquiries: Screens
    @Serializable
    data object ServiceIntroduce: Screens
    @Serializable
    data object Terms: Screens
    @Serializable
    data object PrivacyPolicy: Screens
    @Serializable
    data class Reports(val typeId: Int): Screens
    @Serializable
    data class WebView(val webViewUrl: String): Screens
    @Serializable
    data class RecruitmentPreview(
        val recruitmentId: Int,
        val description: String,
        val recruitingCount: Int,
        val main: String,
        val sub: String,
    ): Screens
    @Serializable
    data class RecruitmentCreatePreview(
        val partyId: Int,
        val description: String,
        val recruitingCount: Int,
        val main: String,
        val sub: String,
    ): Screens
    @Serializable
    data class RecoverAuth(
        val email: String,
        val deletedAt: String,
        val recoverAccessToken:String,
    ): Screens
    @Serializable
    data object Notification: Screens

    @Serializable
    data object HomeDetailProfile: Screens
    @Serializable
    data object HomeDetailProfileLocation: Screens
    @Serializable
    data object HomeDetailProfileCareer: Screens
    @Serializable
    data class HomeDetailChoiceCarrierPosition(
        val isMain: Boolean
    ): Screens
    @Serializable
    data object Trait1: Screens
    @Serializable
    data object Trait2: Screens
    @Serializable
    data object Trait3: Screens
    @Serializable
    data object Trait4: Screens
}