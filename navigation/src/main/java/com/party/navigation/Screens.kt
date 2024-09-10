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
    data object JoinEmail: Screens {
        override val title: String = NavigationTitle.JOIN_EMAIL
        override val icon: ImageVector? = null
    }
    @Serializable
    data object JoinNickName: Screens {
        override val title: String = NavigationTitle.JOIN_NICK_NAME
        override val icon: ImageVector? = null
    }
    @Serializable
    data object JoinBirthDay: Screens {
        override val title: String = NavigationTitle.JOIN_BIRTHDAY
        override val icon: ImageVector? = null
    }
    @Serializable
    data object JoinGender: Screens {
        override val title: String = NavigationTitle.JOIN_GENDER
        override val icon: ImageVector? = null
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
}