package com.party.presentation.screen.guide_permission

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.party.common.utils.HeightSpacer
import com.party.common.Screens
import com.party.common.component.permission.CheckAlarmPermission
import com.party.guam.design.GRAY100
import com.party.guam.design.MEDIUM_PADDING_SIZE
import com.party.guam.design.WHITE
import com.party.presentation.screen.guide_permission.component.GuidePermissionConfirmButton
import com.party.presentation.screen.guide_permission.component.GuidePermissionDescriptionArea
import com.party.presentation.screen.guide_permission.component.GuidePermissionListArea
import com.party.presentation.screen.guide_permission.component.GuidePermissionScaffoldArea
import com.party.presentation.screen.guide_permission.component.GuidePermissionTitleArea
import com.party.presentation.screen.guide_permission.viewmodel.GuidePermissionViewModel

@Composable
fun GuidePermissionScreenRoute(
    context: Context,
    navController: NavHostController,
    guidePermissionViewModel: GuidePermissionViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = Unit) {
        guidePermissionViewModel.saveFirstLaunchFlag()
    }

    GuidePermissionScreen(
        context = context,
        onConfirm = {
            navController.navigate(Screens.Login){
                popUpTo(Screens.GuidePermission) { inclusive = true}
            }
        }
    )
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
private fun GuidePermissionScreen(
    context: Context,
    onConfirm: () -> Unit,
) {
    Scaffold (
        topBar = {
            GuidePermissionScaffoldArea()
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
                HeightSpacer(32.dp)
                GuidePermissionTitleArea()
                HeightSpacer(40.dp)
                GuidePermissionListArea()
                HeightSpacer(20.dp)

                HorizontalDivider(
                    thickness = 1.dp,
                    color = GRAY100
                )

                HeightSpacer(20.dp)

                GuidePermissionDescriptionArea()
            }

            GuidePermissionConfirmButton(
                onClick = onConfirm
            )
            HeightSpacer(12.dp)
        }

        CheckAlarmPermission(
            context = context,
            onGrant = {},
            onDeny = {},
            onDenied = {},
        )
    }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Preview(showBackground = true)
@Composable
fun GuidePermissionScreenPreview(){
    GuidePermissionScreen(
        context = LocalContext.current,
        onConfirm = {}
    )
}