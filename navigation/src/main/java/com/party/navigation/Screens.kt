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
}