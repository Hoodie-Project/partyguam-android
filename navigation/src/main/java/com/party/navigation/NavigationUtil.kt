package com.party.navigation

import androidx.navigation.NavBackStackEntry

fun NavBackStackEntry?.fromRoute(): Screens {
    this?.destination?.route?.substringBefore("?")?.substringBefore("/")?.substringAfterLast(".")?.let {
        return when (it) {
            Screens.Login::class.simpleName -> Screens.Login
            else -> Screens.Login
        }
    }
    return Screens.Login
}