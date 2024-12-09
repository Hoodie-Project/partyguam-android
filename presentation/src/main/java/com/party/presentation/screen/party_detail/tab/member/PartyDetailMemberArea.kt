package com.party.presentation.screen.party_detail.tab.member

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.party.common.HeightSpacer
import com.party.domain.model.party.PartyUsers
import com.party.domain.model.user.PartyAuthority
import com.party.presentation.screen.party_detail.PartyDetailState
import com.party.presentation.screen.party_detail.component.PartyDetailTitleArea
import com.party.presentation.screen.party_detail.tab.member.component.PartyDetailUsersListArea

@Composable
fun PartyDetailUserArea(
    state: PartyDetailState,
) {
    PartyDetailUsersAreaContent(
        partyUsers = state.partyUser,
        authority = state.partyAuthority,
    )
}

@Composable
private fun PartyDetailUsersAreaContent(
    partyUsers: PartyUsers,
    authority: PartyAuthority
) {
    val partyAdminListSize = partyUsers.partyAdmin.size
    val partyUserListSize = partyUsers.partyUser.size

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp)
    ) {
        PartyDetailTitleArea(
            title = "파티원",
            number = "${partyAdminListSize + partyUserListSize}"
        )
        HeightSpacer(heightDp = 24.dp)

        PartyDetailUsersListArea(
            partyUsers = partyUsers,
            authority = authority
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PartyDetailUsersAreaContentPreview() {
    val partyUsers = PartyUsers(
        partyAdmin = listOf(),
        partyUser = listOf()
    )
    val authority = PartyAuthority(
        authority = "",
        userId = 0
    )

    PartyDetailUsersAreaContent(
        partyUsers = partyUsers,
        authority = authority
    )
}