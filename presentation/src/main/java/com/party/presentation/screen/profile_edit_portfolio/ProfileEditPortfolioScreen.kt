package com.party.presentation.screen.profile_edit_portfolio

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.party.common.HeightSpacer
import com.party.common.ui.theme.MEDIUM_PADDING_SIZE
import com.party.common.ui.theme.WHITE
import com.party.presentation.screen.profile_edit_portfolio.component.InputLinkAndTitleArea
import com.party.presentation.screen.profile_edit_portfolio.component.ProfileEditPortfolioScaffoldArea
import com.party.presentation.screen.profile_edit_portfolio.component.ResetAndApplyButtonArea
import com.party.presentation.screen.profile_edit_portfolio.viewmodel.ProfileEditPortfolioViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun ProfileEditPortfolioScreenRoute(
    navController: NavHostController,
    profileEditPortfolioViewModel: ProfileEditPortfolioViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = Unit) {
        profileEditPortfolioViewModel.successState.collectLatest {
             navController.popBackStack()
        }
    }

    val state by profileEditPortfolioViewModel.state.collectAsStateWithLifecycle()

    ProfileEditPortfolioScreen(
        state = state,
        onNavigationClick = { navController.popBackStack() },
        onAction = { action ->
            when(action){
                is ProfileEditPortfolioAction.OnChangeLink -> profileEditPortfolioViewModel.action(action)
                is ProfileEditPortfolioAction.OnChangeTitle -> profileEditPortfolioViewModel.action(action)
                is ProfileEditPortfolioAction.OnReset -> profileEditPortfolioViewModel.action(action)
                is ProfileEditPortfolioAction.OnApply -> profileEditPortfolioViewModel.action(action)
                is ProfileEditPortfolioAction.OnDeleteLink -> profileEditPortfolioViewModel.action(action)
                is ProfileEditPortfolioAction.OnDeleteTitle -> profileEditPortfolioViewModel.action(action)
            }
        }
    )
}

@Composable
private fun ProfileEditPortfolioScreen(
    state: ProfileEditPortfolioState,
    onNavigationClick: () -> Unit,
    onAction: (ProfileEditPortfolioAction) -> Unit,
) {
    Scaffold(
        topBar = {
            ProfileEditPortfolioScaffoldArea(
                onNavigationClick = onNavigationClick
            )
        }
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(WHITE)
                .padding(it)
                .padding(horizontal = MEDIUM_PADDING_SIZE)
        ) {
            // 입력 Area
            InputLinkAndTitleArea(
                modifier = Modifier.weight(1f),
                linkText = state.linkText,
                onLinkValueChange = { inputLink -> onAction(ProfileEditPortfolioAction.OnChangeLink(inputLink)) },
                titleText = state.titleText,
                onTitleValueChange = { inputTitle -> onAction(ProfileEditPortfolioAction.OnChangeTitle(inputTitle)) },
                onDeleteLink = { onAction(ProfileEditPortfolioAction.OnDeleteLink) },
                onDeleteTitle = { onAction(ProfileEditPortfolioAction.OnDeleteTitle) }
            )

            // 초기화 / 적용하기
            ResetAndApplyButtonArea(
                onReset = { onAction(ProfileEditPortfolioAction.OnReset) },
                onApply = { onAction(ProfileEditPortfolioAction.OnApply) }
            )
            HeightSpacer(heightDp = 12.dp)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ProfileEditPortfolioScreenPreview() {
    ProfileEditPortfolioScreen(
        state = ProfileEditPortfolioState(),
        onNavigationClick = {},
        onAction = {}
    )
}