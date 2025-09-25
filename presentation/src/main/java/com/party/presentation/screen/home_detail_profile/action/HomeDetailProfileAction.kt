package com.party.presentation.screen.home_detail_profile.action

import com.party.domain.model.user.detail.Location
import com.party.domain.model.user.detail.PersonalityList
import com.party.domain.model.user.detail.PersonalityListOption
import com.party.domain.model.user.detail.PositionList

sealed interface HomeDetailProfileAction {
    data class OnShowFinishDialog(val isShow: Boolean): HomeDetailProfileAction

    data class OnClickProvince(val provinceName: String): HomeDetailProfileAction
    data class OnClickSubLocation(val location: Location): HomeDetailProfileAction
    data class OnDeleteSelectedLocation(val locationPair: Pair<String, Location>): HomeDetailProfileAction

    data class OnClickFirstCareer(val career: String): HomeDetailProfileAction
    data class OnClickFirstMainPosition(val mainPosition: String): HomeDetailProfileAction
    data class OnClickFirstSubPosition(val positionList: PositionList): HomeDetailProfileAction

    data class OnClickSecondCareer(val career: String): HomeDetailProfileAction
    data class OnClickSecondMainPosition(val mainPosition: String): HomeDetailProfileAction
    data class OnClickSecondSubPosition(val positionList: PositionList): HomeDetailProfileAction

    data object OnResetFirst: HomeDetailProfileAction
    data object OnResetSecond: HomeDetailProfileAction

    data class OnSelectTrait1(val personalityListOption: PersonalityListOption): HomeDetailProfileAction
    data class OnSelectTrait2(val personalityListOption: PersonalityListOption): HomeDetailProfileAction
    data class OnSelectTrait3(val personalityListOption: PersonalityListOption): HomeDetailProfileAction
    data class OnSelectTrait4(val personalityListOption: PersonalityListOption): HomeDetailProfileAction
}