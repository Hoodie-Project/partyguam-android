package com.party.presentation.screen.recruitment_edit

import com.party.domain.model.user.detail.PositionList

sealed interface RecruitmentEditAction {
    data class OnChangeMainPositionBottomSheet(val isMainPositionBottomSheetShow: Boolean): RecruitmentEditAction
    data class OnChangeMainPosition(val position: String): RecruitmentEditAction
    data class OnChangeSubPosition(val positionList: PositionList): RecruitmentEditAction

    data class OnSetSelectedCount(val selectedCount: String): RecruitmentEditAction
    data class OnChangePeopleCountSheet(val isPeopleCountSheetOpen: Boolean): RecruitmentEditAction

    data class OnChangeHelpCardOpen(val isHelpCardOpen: Boolean): RecruitmentEditAction
    data class OnChangeRecruitmentDescription(val recruitmentDescription: String): RecruitmentEditAction

    data class OnModifyRecruitment(val partyId: Int, val partyRecruitmentId: Int): RecruitmentEditAction
}