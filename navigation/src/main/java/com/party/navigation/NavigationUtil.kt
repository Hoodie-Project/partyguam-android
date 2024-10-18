package com.party.navigation

import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavType
import com.party.domain.model.user.detail.PersonalitySaveRequest
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

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
            Screens.ChoiceCarrierPosition::class.simpleName -> Screens.ChoiceCarrierPosition("", true)
            Screens.SelectTendency1::class.simpleName -> Screens.SelectTendency1
            Screens.SelectTendency2::class.simpleName -> Screens.SelectTendency2(personalitySaveRequest = PersonalitySaveRequest(personality = emptyList()))
            Screens.SelectTendency3::class.simpleName -> Screens.SelectTendency3(personalitySaveRequest = PersonalitySaveRequest(personality = emptyList()))
            Screens.SelectTendency4::class.simpleName -> Screens.SelectTendency4(personalitySaveRequest = PersonalitySaveRequest(personality = emptyList()))
            Screens.SelectTendencyComplete::class.simpleName -> Screens.SelectTendencyComplete
            Screens.Home::class.simpleName -> Screens.Home
            Screens.State::class.simpleName -> Screens.State
            Screens.Profile::class.simpleName -> Screens.Profile
            else -> Screens.Login
        }
    }
    return Screens.Splash
}

inline fun <reified T : Parcelable> parcelableType(
    isNullableAllowed: Boolean = false,
    json: Json = Json,
) = object : NavType<T>(isNullableAllowed = isNullableAllowed) {
    override fun get(bundle: Bundle, key: String) =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            bundle.getParcelable(key, T::class.java)
        } else {
            @Suppress("DEPRECATION")
            bundle.getParcelable(key)
        }

    override fun parseValue(value: String): T = json.decodeFromString(value)

    override fun serializeAsValue(value: T): String = json.encodeToString(value)

    override fun put(bundle: Bundle, key: String, value: T) = bundle.putParcelable(key, value)
}