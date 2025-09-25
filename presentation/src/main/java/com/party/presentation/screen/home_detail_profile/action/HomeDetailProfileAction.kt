package com.party.presentation.screen.home_detail_profile.action

import com.party.domain.model.user.detail.Location

sealed interface HomeDetailProfileAction {
    data class OnClickProvince(val provinceName: String): HomeDetailProfileAction
    data class OnClickSubLocation(val location: Location): HomeDetailProfileAction
    data class OnDeleteSelectedLocation(val locationPair: Pair<String, Location>): HomeDetailProfileAction

}