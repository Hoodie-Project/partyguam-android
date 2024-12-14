package com.party.presentation.screen.recruitment_create

import com.party.domain.model.user.detail.PositionList

sealed interface RecruitmentCreateAction {

    data class OnChangeMainPositionBottomSheet(val isMainPositionBottomSheetShow: Boolean): RecruitmentCreateAction
    data class OnChangeMainPosition(val position: String): RecruitmentCreateAction
    data class OnChangeSubPosition(val positionList: PositionList): RecruitmentCreateAction
    data class OnRecruitmentCreate(val partyId: Int): RecruitmentCreateAction

    data class OnSetSelectedCount(val selectedCount: String): RecruitmentCreateAction
    data class OnChangePeopleCountSheet(val isPeopleCountSheetOpen: Boolean): RecruitmentCreateAction

    data class OnChangeHelpCardOpen(val isHelpCardOpen: Boolean): RecruitmentCreateAction
    data class OnChangeRecruitmentDescription(val recruitmentDescription: String): RecruitmentCreateAction
}