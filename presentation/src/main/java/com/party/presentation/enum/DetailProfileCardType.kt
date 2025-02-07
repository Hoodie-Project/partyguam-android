package com.party.presentation.enum

import com.party.common.Screens

enum class DetailProfileCardType(
    val title: String,
    val content: String,
    val screens: Screens,
) {
    CAREER_POSITION(
        title = "경력/포지션",
        content = "경력과 포지션을 입력하면\n파티 합류 가능성 UP",
        screens = Screens.ProfileEditCareer,
    ),
    LIKE_LOCATION(
        title = "관심 장소",
        content = "관심 지역을 선택하고\n파티를 추천 받아보세요!",
        screens = Screens.ProfileEditLocation,
    ),
    LIKE_TIME(
        title = "희망 시간",
        content = "주로 작업하는 시간대는\n어떻게 되시나요?",
        screens = Screens.ProfileEditTime,
    ),
    CHECK_PERSONALITY(
        title = "성향 체크",
        content = "성향을 체크하고\n파티원을 추천받으세요",
        screens = Screens.ProfileEditTendency,
    ),
}
