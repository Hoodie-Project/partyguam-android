package com.party.presentation.screen.home

import com.party.common.component.homeTopTabList
import com.party.domain.model.banner.Banner
import com.party.domain.model.party.PartyList
import com.party.domain.model.party.PersonalRecruitmentList
import com.party.domain.model.party.RecruitmentList

data class HomeState(

    // 메인 / 파티 / 모집공고 탭
    val selectedTabText: String = homeTopTabList[0],

    // Main Banner
    val isLoadingBanner: Boolean = false,
    val banner: Banner = Banner(0, emptyList()),

    // Main 개인 맞춤 모집공고
    val isLoadingPersonalRecruitmentList: Boolean = false,
    val personalRecruitmentList: PersonalRecruitmentList = PersonalRecruitmentList(emptyList(), 0),
    val isNotProfileError: Boolean = false,

    // Main 모집공고
    val isLoadingRecruitmentList: Boolean = false,
    val recruitmentList: RecruitmentList = RecruitmentList(emptyList(), 0),

    // Main 파티
    val isLoadingPartyList: Boolean = false,
    val partyList: PartyList = PartyList(emptyList(), 0),
)