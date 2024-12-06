package com.party.presentation.screen.home.preview

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.party.common.component.PartyListItem1
import com.party.domain.model.party.PersonalRecruitmentItem
import com.party.domain.model.party.PersonalRecruitmentParty
import com.party.domain.model.party.PersonalRecruitmentPartyType
import com.party.domain.model.party.PersonalRecruitmentPosition
import com.party.presentation.screen.home.PartyCategory
import com.party.presentation.screen.home.tab_main.PersonalRecruitmentItem

@Preview(showBackground = true)
@Composable
private fun PersonalRecruitmentItemPreview() {
    PersonalRecruitmentItem(
        personalRecruitmentLisItemResponse = PersonalRecruitmentItem(
            id = 1,
            partyId = 1,
            positionId = 1,
            content = "내용",
            recruitingCount = 1,
            recruitedCount = 1,
            createdAt = "2021-09-01",
            party = PersonalRecruitmentParty(
                title = "파티 제목",
                image = "https://picsum.photos/200/300",
                partyType = PersonalRecruitmentPartyType(
                    id = 1,
                    type = "타입"
                )
            ),
            position = PersonalRecruitmentPosition(
                id = 1,
                main = "메인",
                sub = "서브"
            )
        ),
        onClick = {}
    )
}

@Preview(showBackground = true)
@Composable
private fun PersonalRecruitmentItemPreview2() {
    PersonalRecruitmentItem(
        personalRecruitmentLisItemResponse = PersonalRecruitmentItem(
            id = 1,
            partyId = 1,
            positionId = 1,
            content = "내용",
            recruitingCount = 1,
            recruitedCount = 1,
            createdAt = "2021-09-01",
            party = PersonalRecruitmentParty(
                title = "파티 제목파티 제목파티 제목파티 제목파티 제목파티 제목파티 제목123",
                image = "https://picsum.photos/200/300",
                partyType = PersonalRecruitmentPartyType(
                    id = 1,
                    type = "타입"
                )
            ),
            position = PersonalRecruitmentPosition(
                id = 1,
                main = "메인",
                sub = "서브"
            )
        ),
        onClick = {}
    )
}

@Preview(showBackground = true)
@Composable
private fun PartyItemPreview() {
    PartyListItem1(
        imageUrl = "",
        status = "포트폴리오",
        title = "포르폴리오 할 사람",
        recruitmentCount = 1,
        onClick = {}
    )
}

@Preview(showBackground = true)
@Composable 
private fun PartyCategoryPreview(
    modifier: Modifier = Modifier
) {
    PartyCategory(category = "미정")
}