package com.party.navigation

import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.serialization.Serializable

interface ArgInterface {
    val title: String
    val icon: ImageVector?
}

sealed interface Screens : ArgInterface {
    @Serializable
    data object Login: Screens {
        override val title: String = NavigationTitle.LOGIN
        override val icon: ImageVector? = null
    }
    @Serializable
    data class JoinEmail(val userEmail: String, val signupAccessToken: String): Screens {
        override val title: String = NavigationTitle.JOIN_EMAIL
        override val icon: ImageVector? get() = null
    }
    @Serializable
    data class JoinNickName(val userEmail: String, val signupAccessToken: String): Screens {
        override val title: String = NavigationTitle.JOIN_NICK_NAME
        override val icon: ImageVector? get() = null
    }
    @Serializable
    data class JoinBirthDay(val userEmail: String, val signupAccessToken: String, val userNickName: String): Screens {
        override val title: String = NavigationTitle.JOIN_BIRTHDAY
        override val icon: ImageVector? get() = null
    }
    @Serializable
    data class JoinGender(val userEmail: String, val signupAccessToken: String, val userNickName: String, val userBirthDay: String): Screens {
        override val title: String = NavigationTitle.JOIN_GENDER
        override val icon: ImageVector? get() = null
    }
    @Serializable
    data object JoinComplete: Screens {
        override val title: String = NavigationTitle.JOIN_COMPLETE
        override val icon: ImageVector? = null
    }
    @Serializable
    data object DetailProfile: Screens {
        override val title: String = NavigationTitle.DETAIL_PROFILE
        override val icon: ImageVector? = null
    }
    @Serializable
    data object DetailCarrier: Screens {
        override val title: String = NavigationTitle.DETAIL_CARRIER
        override val icon: ImageVector? = null
    }
    @Serializable
    data object ChoiceCarrierPosition: Screens {
        override val title: String = NavigationTitle.CHOICE_CARRIER_POSITION
        override val icon: ImageVector? = null
    }
    @Serializable
    data object SelectTendency1: Screens {
        override val title: String = NavigationTitle.SELECT_TENDENCY
        override val icon: ImageVector? = null
    }
    @Serializable
    data object SelectTendency2: Screens {
        override val title: String = NavigationTitle.SELECT_TENDENCY
        override val icon: ImageVector? = null
    }
    @Serializable
    data object SelectTendency3: Screens {
        override val title: String = NavigationTitle.SELECT_TENDENCY
        override val icon: ImageVector? = null
    }
    @Serializable
    data object SelectTendency4: Screens {
        override val title: String = NavigationTitle.SELECT_TENDENCY
        override val icon: ImageVector? = null
    }
    @Serializable
    data object SelectTendencyComplete: Screens {
        override val title: String = NavigationTitle.SELECT_TENDENCY
        override val icon: ImageVector? = null
    }
    @Serializable
    data object Home: Screens {
        override val title: String = NavigationTitle.HOME
        override val icon: ImageVector? = null
    }
}