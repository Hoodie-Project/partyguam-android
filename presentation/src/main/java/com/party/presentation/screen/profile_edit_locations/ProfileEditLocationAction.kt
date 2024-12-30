package com.party.presentation.screen.profile_edit_locations

import com.party.domain.model.user.detail.Location

sealed interface ProfileEditLocationAction {
    data class OnSelectProvince(val province: String): ProfileEditLocationAction
    data class OnSelectLocation(val location: Location): ProfileEditLocationAction
    data class OnDeleteProvinceAndLocation(val selectedProvinceAndLocation: Pair<String, Location>): ProfileEditLocationAction
    data object OnReset: ProfileEditLocationAction
    data object OnApply: ProfileEditLocationAction
}