package com.party.navigation

import com.party.domain.model.user.detail.PersonalitySaveRequest
import kotlinx.serialization.Serializable

interface ArgInterface {
    val title: String
}

sealed interface Screens: ArgInterface {
    @Serializable
    data object Splash: Screens {
        override val title: String = NavigationTitle.SPLASH
    }
    @Serializable
    data object Login: Screens {
        override val title: String = NavigationTitle.LOGIN
    }
    @Serializable
    data class JoinEmail(val userEmail: String, val signupAccessToken: String): Screens {
        override val title: String = NavigationTitle.JOIN_EMAIL
    }
    @Serializable
    data class JoinNickName(val userEmail: String, val signupAccessToken: String): Screens {
        override val title: String = NavigationTitle.JOIN_NICK_NAME
    }
    @Serializable
    data class JoinBirthDay(val userEmail: String, val signupAccessToken: String, val userNickName: String): Screens {
        override val title: String = NavigationTitle.JOIN_BIRTHDAY
    }
    @Serializable
    data class JoinGender(val userEmail: String, val signupAccessToken: String, val userNickName: String, val userBirthDay: String): Screens {
        override val title: String = NavigationTitle.JOIN_GENDER
    }
    @Serializable
    data object JoinComplete: Screens {
        override val title: String = NavigationTitle.JOIN_COMPLETE
    }
    @Serializable
    data object DetailProfile: Screens {
        override val title: String = NavigationTitle.DETAIL_PROFILE
    }
    @Serializable
    data object DetailCarrier: Screens {
        override val title: String = NavigationTitle.DETAIL_CARRIER
    }
    @Serializable
    data class ChoiceCarrierPosition(val accessToken: String, val isMain: Boolean): Screens {
        override val title: String = NavigationTitle.CHOICE_CARRIER_POSITION
    }
    @Serializable
    data object SelectTendency1: Screens {
        override val title: String = NavigationTitle.SELECT_TENDENCY
    }
    @Serializable
    data class SelectTendency2(val personalitySaveRequest: PersonalitySaveRequest): Screens {
        override val title: String = NavigationTitle.SELECT_TENDENCY
    }
    @Serializable
    data class SelectTendency3(val personalitySaveRequest: PersonalitySaveRequest): Screens {
        override val title: String = NavigationTitle.SELECT_TENDENCY
    }
    @Serializable
    data class SelectTendency4(val personalitySaveRequest: PersonalitySaveRequest): Screens {
        override val title: String = NavigationTitle.SELECT_TENDENCY
    }
    @Serializable
    data object SelectTendencyComplete: Screens {
        override val title: String = NavigationTitle.SELECT_TENDENCY
    }
    @Serializable
    data object Home: Screens {
        override val title: String = NavigationTitle.HOME
    }
    @Serializable
    data object State: Screens {
        override val title: String = NavigationTitle.STATE
    }
    @Serializable
    data object Profile: Screens {
        override val title: String = NavigationTitle.PROFILE
    }
}