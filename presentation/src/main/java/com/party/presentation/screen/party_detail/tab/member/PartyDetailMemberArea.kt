package com.party.presentation.screen.party_detail.tab.member

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.party.common.HeightSpacer
import com.party.common.LoadingProgressBar
import com.party.common.R
import com.party.common.ServerApiResponse
import com.party.common.ServerApiResponse.SuccessResponse
import com.party.common.UIState
import com.party.common.snackBarMessage
import com.party.domain.model.party.PartyUsers
import com.party.domain.model.user.PartyAuthority
import com.party.presentation.screen.home.tab_main.ErrorArea
import com.party.presentation.screen.party_detail.component.PartyDetailTitleArea
import com.party.presentation.screen.party_detail.tab.member.component.PartyDetailUsersListArea

@Composable
fun PartyDetailUserArea(
    snackBarHostState: SnackbarHostState,
    partyUsersState: UIState<ServerApiResponse<PartyUsers>>,
    partyAuthorityState: UIState<ServerApiResponse<PartyAuthority>>,
) {
    var authority by remember {
        mutableStateOf(
            PartyAuthority(
                authority = "",
                userId = 0
            )
        )
    }

    when(partyAuthorityState){
        is UIState.Idle -> {}
        is UIState.Loading -> { LoadingProgressBar() }
        is UIState.Success -> {
            val result = partyAuthorityState.data as SuccessResponse<PartyAuthority>
            authority = result.data ?: PartyAuthority(
                authority = "",
                userId = 0
            )
        }
        is UIState.Error -> {
            authority = PartyAuthority(
                authority = "",
                userId = 0
            )
        }
        is UIState.Exception -> { snackBarMessage(message = stringResource(id = R.string.common6), snackBarHostState = snackBarHostState) }
    }

    when(partyUsersState){
        is UIState.Idle -> {}
        is UIState.Loading -> { LoadingProgressBar() }
        is UIState.Success -> {
            val successResult = partyUsersState.data as SuccessResponse<PartyUsers>
            PartyDetailUsersAreaContent(
                partyUsers = successResult.data ?: return,
                authority = authority,
            )
        }
        is UIState.Error -> { ErrorArea() }
        is UIState.Exception -> { snackBarMessage(message = stringResource(id = R.string.common6), snackBarHostState = snackBarHostState) }
    }
}

@Composable
fun PartyDetailUsersAreaContent(
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