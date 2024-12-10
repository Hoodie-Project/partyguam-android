package com.party.presentation.screen.party_create

import com.party.domain.model.user.detail.PositionList
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody

data class PartyCreateState(
    val isBackShowDialog: Boolean = false, // 뒤로가기 다이어로그
    val isVisibleToolTip: Boolean = true, // 툴팁

    val image: MultipartBody.Part = MultipartBody.Part.createFormData("image", "", byteArrayOf().toRequestBody()), // 이미지

    val inputPartyTitle: String = "", // 파티 제목
    val isPartyTypeSheetOpen: Boolean = false, // 파티 타입 시트
    val selectedPartyType: String = "", // 선택된 파티 타입 (미정, 스터디, 포트폴리오, 해커톤, 공모전)
    val isHelpCardOpen: Boolean = false, // 도움말 카드

    val partyDescription: String = "", // 파티 설명
    val selectedMainPosition: String = "", // 선택된 메인 포지션
    val selectedSubPosition: PositionList = PositionList(0, "", ""), // 선택된 서브 포지션

    val isLoading: Boolean = false, // 파티 생성 로딩
    val isCompleteShowDialog: Boolean = false, // 완료 다이어로그
    val partyId: Int = 0, // 파티가 생성된 후 id
)
