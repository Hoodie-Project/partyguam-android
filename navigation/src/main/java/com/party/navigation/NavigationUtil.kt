package com.party.navigation

import androidx.navigation.NavBackStackEntry

fun NavBackStackEntry?.fromRoute(): Screens {
    this?.destination?.route?.substringBefore("?")?.substringBefore("/")?.substringAfterLast(".")?.let {
        return when (it) {
            Screens.Login::class.simpleName -> Screens.Login
            Screens.JoinEmail::class.simpleName -> Screens.JoinEmail
            Screens.JoinNickName::class.simpleName -> Screens.JoinNickName
            Screens.JoinBirthDay::class.simpleName -> Screens.JoinBirthDay
            Screens.JoinGender::class.simpleName -> Screens.JoinGender
            Screens.JoinComplete::class.simpleName -> Screens.JoinComplete
            Screens.DetailProfile::class.simpleName -> Screens.DetailProfile
            Screens.DetailCarrier::class.simpleName -> Screens.DetailCarrier
            else -> Screens.Login
        }
    }
    return Screens.Login
}