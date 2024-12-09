package com.party.presentation.screen.party_detail.component

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.party.common.LoadingProgressBar
import com.party.common.R
import com.party.common.ServerApiResponse
import com.party.common.ServerApiResponse.SuccessResponse
import com.party.common.UIState
import com.party.common.component.icon.DrawableIconButton
import com.party.common.component.scaffold.ScaffoldCenterBar
import com.party.common.snackBarMessage
import com.party.common.ui.theme.BLACK
import com.party.domain.model.user.PartyAuthority
import com.party.presentation.enum.PartyAuthorityType
import com.party.presentation.screen.party_detail.PartyDetailState

@Composable
fun PartyDetailScaffoldArea(
    state: PartyDetailState,
    onNavigationClick: () -> Unit,
    onSharedClick: () -> Unit,
    onMoreClick: () -> Unit,
) {
    ScaffoldCenterBar(
        navigationIcon = {
            DrawableIconButton(
                icon = painterResource(id = R.drawable.arrow_back),
                iconColor = BLACK,
                iconSize = 24.dp,
                contentDescription = "back",
                onClick = { onNavigationClick() }
            )
        },
        actionIcons = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                DrawableIconButton(
                    icon = painterResource(id = R.drawable.icon_share),
                    iconColor = BLACK,
                    iconSize = 24.dp,
                    contentDescription = "share",
                    onClick = { onSharedClick() }
                )

                if(state.partyAuthority.authority != PartyAuthorityType.MASTER.authority) {
                    DrawableIconButton(
                        icon = painterResource(id = R.drawable.icon_more),
                        iconColor = BLACK,
                        iconSize = 24.dp,
                        contentDescription = "more",
                        onClick = { onMoreClick() }
                    )
                }
            }
        }
    )
}