package com.party.navigation

import androidx.navigation.NavBackStackEntry

fun NavBackStackEntry?.fromRoute(): Screens {
    this?.destination?.route?.substringBefore("?")?.substringBefore("/")?.substringAfterLast(".")?.let {
        return when (it) {
            Screens.Splash::class.simpleName -> Screens.Splash
            Screens.Login::class.simpleName -> Screens.Login
            Screens.JoinEmail::class.simpleName -> Screens.JoinEmail("", "")
            Screens.JoinNickName::class.simpleName -> Screens.JoinNickName("", "")
            Screens.JoinBirthDay::class.simpleName -> Screens.JoinBirthDay("", "", "")
            Screens.JoinGender::class.simpleName -> Screens.JoinGender("", "", "", "")
            Screens.JoinComplete::class.simpleName -> Screens.JoinComplete
            Screens.DetailProfile::class.simpleName -> Screens.DetailProfile
            Screens.DetailCarrier::class.simpleName -> Screens.DetailCarrier
            Screens.ChoiceCarrierPosition::class.simpleName -> Screens.ChoiceCarrierPosition(true)
            Screens.SelectTendency1::class.simpleName -> Screens.SelectTendency1
            Screens.SelectTendency2::class.simpleName -> Screens.SelectTendency2
            Screens.SelectTendency3::class.simpleName -> Screens.SelectTendency3
            Screens.SelectTendency4::class.simpleName -> Screens.SelectTendency4
            Screens.SelectTendencyComplete::class.simpleName -> Screens.SelectTendencyComplete
            Screens.Home::class.simpleName -> Screens.Home
            Screens.State::class.simpleName -> Screens.State
            Screens.Profile::class.simpleName -> Screens.Profile
            Screens.RecruitmentDetail::class.simpleName -> Screens.RecruitmentDetail(partyRecruitmentId = 1)
            else -> Screens.Login
        }
    }
    return Screens.Splash
}