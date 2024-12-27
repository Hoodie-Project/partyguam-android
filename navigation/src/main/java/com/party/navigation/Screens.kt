package com.party.navigation

import kotlinx.serialization.Serializable

sealed interface Screens {
    @Serializable
    data object Splash: Screens
    @Serializable
    data object Login: Screens
    @Serializable
    data class JoinEmail(val userEmail: String, val signupAccessToken: String): Screens
    @Serializable
    data class JoinNickName(val userEmail: String, val signupAccessToken: String): Screens
    @Serializable
    data class JoinBirthDay(val userEmail: String, val signupAccessToken: String, val userNickName: String): Screens
    @Serializable
    data class JoinGender(val userEmail: String, val signupAccessToken: String, val userNickName: String, val userBirthDay: String): Screens
    @Serializable
    data object JoinComplete: Screens
    @Serializable
    data object DetailProfile: Screens
    @Serializable
    data object DetailCarrier: Screens
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
}