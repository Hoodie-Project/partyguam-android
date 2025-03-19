package com.party.presentation.screen.recover_auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.party.common.HeightSpacer
import com.party.common.ui.theme.MEDIUM_PADDING_SIZE
import com.party.common.ui.theme.WHITE
import com.party.presentation.screen.recover_auth.component.AuthInfoArea
import com.party.presentation.screen.recover_auth.component.RecoverAuthScaffoldArea
import com.party.presentation.screen.recover_auth.component.RecoverBottomDescriptionArea
import com.party.presentation.screen.recover_auth.component.RecoverButton
import com.party.presentation.screen.recover_auth.component.RecoverDescriptionArea

@Composable
fun RecoverAuthScreenRoute(
    navController: NavHostController,
) {
    RecoverAuthScreen(
        onNavigationClick = { navController.popBackStack() },
        onRecover = {}
    )
}

@Composable
private fun RecoverAuthScreen(
    onNavigationClick: () -> Unit,
    onRecover: () -> Unit,
) {
    Scaffold(
        topBar = {
            RecoverAuthScaffoldArea(
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
            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                HeightSpacer(heightDp = 28.dp)
                RecoverDescriptionArea()
                HeightSpacer(heightDp = 32.dp)

                AuthInfoArea()
                HeightSpacer(heightDp = 20.dp)

                RecoverBottomDescriptionArea()
            }

            RecoverButton(
                onClick = onRecover
            )
            HeightSpacer(heightDp = 12.dp)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun RecoverAuthScreenPreview(modifier: Modifier = Modifier) {
    RecoverAuthScreen(
        onNavigationClick = {},
        onRecover = {}
    )
}