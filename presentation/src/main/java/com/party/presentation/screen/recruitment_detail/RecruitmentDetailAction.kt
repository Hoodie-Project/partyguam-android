package com.party.presentation.screen.recruitment_detail

sealed interface RecruitmentDetailAction {
    data object OnNavigationBack: RecruitmentDetailAction
    data object OnApply: RecruitmentDetailAction
}