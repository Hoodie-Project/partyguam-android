package com.party.presentation.screen.terms

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.party.common.HeightSpacer
import com.party.common.TextComponent
import com.party.common.ui.theme.B2
import com.party.common.ui.theme.MEDIUM_PADDING_SIZE
import com.party.common.ui.theme.WHITE
import com.party.presentation.screen.terms.component.PrivacyPolicyContent
import com.party.presentation.screen.terms.component.TermsScaffoldArea
import com.party.presentation.screen.terms.component.privacy_policy.PrivacyPolicyItem

@Composable
fun PrivacyPolicyScreenRoute(
    navController: NavHostController,
) {
    PrivacyPolicyScreen(
        onNavigationClick = { navController.popBackStack() }
    )
}

@Composable
private fun PrivacyPolicyScreen(
    onNavigationClick: () -> Unit,
) {
    val scrollState = rememberScrollState()
    Scaffold(
        topBar = {
            TermsScaffoldArea(
                title = "개인정보 처리방침",
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
                .verticalScroll(scrollState)
        ) {
            HeightSpacer(heightDp = 24.dp)
            TextComponent(
                text = "‘파티구함’(이하 \"회사\" 또는 \"사이트\")은 정보주체의 자유와 권리 보호를 위해 「개인정보 보호법」 및 관계 법령이 정한 바를 준수하여, 적법하게 개인정보를 처리하고 안전하게 관리하기 위해 최선을 다하고 있습니다. 이에 「개인정보 보호법」 제30조에 따라 정보주체에게 개인정보 처리에 관한 절차 및 기준을 안내하고, 이와 관련한 고충을 신속하고 원활하게 처리할 수 있도록 하기 위하여 다음과 같이 개인정보 처리 방침을 수립·공개합니다.",
                fontSize = B2
            )

            HeightSpacer(heightDp = 60.dp)
            PrivacyPolicyItem(
                title = PrivacyPolicyContent.FIRST.title,
                content = PrivacyPolicyContent.FIRST.descriptions
            )
            HeightSpacer(heightDp = 60.dp)
            PrivacyPolicyItem(
                title = PrivacyPolicyContent.SECOND.title,
                content = PrivacyPolicyContent.SECOND.descriptions
            )
            HeightSpacer(heightDp = 60.dp)
            PrivacyPolicyItem(
                title = PrivacyPolicyContent.THIRD.title,
                content = PrivacyPolicyContent.THIRD.descriptions
            )
            HeightSpacer(heightDp = 60.dp)
            PrivacyPolicyItem(
                title = PrivacyPolicyContent.FOURTH.title,
                content = PrivacyPolicyContent.FOURTH.descriptions
            )
            HeightSpacer(heightDp = 60.dp)
            PrivacyPolicyItem(
                title = PrivacyPolicyContent.FIFTH.title,
                content = PrivacyPolicyContent.FIFTH.descriptions
            )
            HeightSpacer(heightDp = 60.dp)
            PrivacyPolicyItem(
                title = PrivacyPolicyContent.SIXTH.title,
                content = PrivacyPolicyContent.SIXTH.descriptions
            )
            HeightSpacer(heightDp = 60.dp)
            PrivacyPolicyItem(
                title = PrivacyPolicyContent.SEVENTH.title,
                content = PrivacyPolicyContent.SEVENTH.descriptions
            )
            HeightSpacer(heightDp = 60.dp)
            PrivacyPolicyItem(
                title = PrivacyPolicyContent.EIGHTH.title,
                content = PrivacyPolicyContent.EIGHTH.descriptions
            )
            HeightSpacer(heightDp = 60.dp)
            PrivacyPolicyItem(
                title = PrivacyPolicyContent.NINTH.title,
                content = PrivacyPolicyContent.NINTH.descriptions
            )
            HeightSpacer(heightDp = 60.dp)
            PrivacyPolicyItem(
                title = PrivacyPolicyContent.TENTH.title,
                content = PrivacyPolicyContent.TENTH.descriptions
            )
            HeightSpacer(heightDp = 60.dp)
            PrivacyPolicyItem(
                title = PrivacyPolicyContent.ELEVENTH.title,
                content = PrivacyPolicyContent.ELEVENTH.descriptions
            )
            HeightSpacer(heightDp = 60.dp)
            PrivacyPolicyItem(
                title = PrivacyPolicyContent.TWELFTH.title,
                content = PrivacyPolicyContent.TWELFTH.descriptions
            )
            HeightSpacer(heightDp = 60.dp)
            PrivacyPolicyItem(
                title = PrivacyPolicyContent.THIRTEENTH.title,
                content = PrivacyPolicyContent.THIRTEENTH.descriptions
            )
            HeightSpacer(heightDp = 60.dp)
            PrivacyPolicyItem(
                title = PrivacyPolicyContent.LAST.title,
                content = PrivacyPolicyContent.LAST.descriptions
            )
            HeightSpacer(heightDp = 60.dp)
        }
    }
}