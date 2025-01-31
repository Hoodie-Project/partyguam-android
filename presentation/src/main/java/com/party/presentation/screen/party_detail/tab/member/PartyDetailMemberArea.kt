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
import com.party.domain.model.user.PartyAuthorityPosition
import com.party.presentation.screen.party_detail.PartyDetailState
import com.party.presentation.screen.party_detail.component.PartyDetailTitleArea
import com.party.presentation.screen.party_detail.tab.member.component.PartyDetailUsersListArea

@Composable
fun PartyDetailUserArea(
    state: PartyDetailState,
    onReports: (Int) -> Unit,
) {
    PartyDetailUsersAreaContent(
        partyUsers = state.partyUser,
        authority = state.partyAuthority,
        onReports = onReports
    )
}

@Composable
private fun PartyDetailUsersAreaContent(
    partyUsers: PartyUsers,
    authority: PartyAuthority,
    onReports: (Int) -> Unit,
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
            number = "${partyAdminListSize + partyUserListSize}",
        )
        HeightSpacer(heightDp = 24.dp)

        PartyDetailUsersListArea(
            partyUsers = partyUsers,
            authority = authority,
            onReports = onReports
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PartyDetailUsersAreaContentPreview() {
    val partyUsers = PartyUsers(
        partyAdmin = listOf(),
        partyUser = listOf()
    )
    val authority = PartyAuthority(
        id = 0,
        authority = "",
        position = PartyAuthorityPosition(0, "", "")
    )

    PartyDetailUsersAreaContent(
        partyUsers = partyUsers,
        authority = authority,
        onReports = {}
    )
}