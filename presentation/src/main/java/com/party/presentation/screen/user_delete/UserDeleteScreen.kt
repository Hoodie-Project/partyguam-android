package com.party.presentation.screen.user_delete

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.party.common.HeightSpacer
import com.party.common.ui.theme.MEDIUM_PADDING_SIZE
import com.party.common.ui.theme.WHITE
import com.party.presentation.screen.user_delete.component.UserDeleteButton
import com.party.presentation.screen.user_delete.component.UserDeleteScaffoldArea
import com.party.presentation.screen.user_delete.component.WarningAgreeArea
import com.party.presentation.screen.user_delete.component.WarningArea
import com.party.presentation.screen.user_delete.component.WarningTitleArea

@Composable
fun UserDeleteScreenRoute(
    navController: NavHostController,
) {
    UserDeleteScreen(
        onNavigationBack = { navController.popBackStack() }
    )
}

@Composable
private fun UserDeleteScreen(
    onNavigationBack: () -> Unit,
) {
    val scrollState = rememberScrollState()
    var isChecked by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            UserDeleteScaffoldArea(
                onNavigationBack = onNavigationBack
            )
        }
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(WHITE)
                .padding(it)
                .padding(horizontal = MEDIUM_PADDING_SIZE)
                .verticalScroll(scrollState)
        ) {
            // 타이틀
            HeightSpacer(heightDp = 16.dp)
            WarningTitleArea()

            // 회원탈퇴 유의사항
            HeightSpacer(heightDp = 24.dp)
            WarningArea()

            // Agree
            HeightSpacer(heightDp = 40.dp)
            WarningAgreeArea(
                isChecked = isChecked,
                onCheckedChange = { checked -> isChecked = checked }
            )

            // Delete Button
            HeightSpacer(heightDp = 40.dp)
            UserDeleteButton(
                isChecked = isChecked,
                onClick = {}
            )
            HeightSpacer(heightDp = 10.dp)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UserDeleteScreenPreview() {
    UserDeleteScreen(
        onNavigationBack = {},
    )
}