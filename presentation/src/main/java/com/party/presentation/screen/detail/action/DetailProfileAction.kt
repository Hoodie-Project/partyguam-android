package com.party.presentation.screen.detail.action

import com.party.domain.model.user.detail.Location
import com.party.domain.model.user.detail.PersonalityListOption
import com.party.domain.model.user.detail.PositionList

sealed interface DetailProfileAction {
    data class OnShowFinishDialog(val isShow: Boolean): DetailProfileAction

    data class OnClickProvince(val provinceName: String): DetailProfileAction
    data class OnClickSubLocation(val location: Location): DetailProfileAction
    data class OnDeleteSelectedLocation(val locationPair: Pair<String, Location>): DetailProfileAction

    data class OnClickFirstCareer(val career: String): DetailProfileAction
    data class OnClickFirstMainPosition(val mainPosition: String): DetailProfileAction
    data class OnClickFirstSubPosition(val positionList: PositionList): DetailProfileAction

    data class OnClickSecondCareer(val career: String): DetailProfileAction
    data class OnClickSecondMainPosition(val mainPosition: String): DetailProfileAction
    data class OnClickSecondSubPosition(val positionList: PositionList): DetailProfileAction

    data object OnResetFirst: DetailProfileAction
    data object OnResetSecond: DetailProfileAction

    data class OnSelectTrait1(val personalityListOption: PersonalityListOption): DetailProfileAction
    data class OnSelectTrait2(val personalityListOption: PersonalityListOption): DetailProfileAction
    data class OnSelectTrait3(val personalityListOption: PersonalityListOption): DetailProfileAction
    data class OnSelectTrait4(val personalityListOption: PersonalityListOption): DetailProfileAction
}