package com.party.presentation.screen.terms

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.party.common.HeightSpacer
import com.party.common.TextComponent
import com.party.common.ui.theme.B2
import com.party.common.ui.theme.MEDIUM_PADDING_SIZE
import com.party.common.ui.theme.WHITE
import com.party.presentation.screen.terms.component.DescriptionTitleArea
import com.party.presentation.screen.terms.component.TermsScaffoldArea

@Composable
fun CustomerInquiriesScreenRoute(
    navController: NavHostController,
) {
    CustomerInquiriesScreen(
        onNavigationClick = { navController.popBackStack() }
    )
}

@Composable
private fun CustomerInquiriesScreen(
    onNavigationClick: () -> Unit,
) {
    Scaffold(
        topBar = {
            TermsScaffoldArea(
                title = "고객문의",
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

            HeightSpacer(heightDp = 24.dp)
            DescriptionTitleArea(
                title = "궁금하신 부분이나, 건의하실 내용은\n언제든 아래로 문의해 주세요."
            )

            HeightSpacer(heightDp = 20.dp)
            InquiriesArea()
        }
    }
}

@Composable
private fun InquiriesArea(
    modifier: Modifier = Modifier
) {
    InquiriesAreaItem(
        title = "이메일",
        content = "hoodiev.team@gmail.com",
    )
    HeightSpacer(heightDp = 20.dp)
    InquiriesAreaItem(
        title = "카카오톡 오픈채팅",
        content = "오픈채팅 주소...",
    )
}

@Composable
private fun InquiriesAreaItem(
    title: String,
    content: String,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        TextComponent(
            text = title,
            fontSize = B2,
            fontWeight = FontWeight.SemiBold
        )
        HeightSpacer(heightDp = 4.dp)
        TextComponent(
            text = content,
            fontSize = B2,
            fontWeight = FontWeight.Normal
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CustomerInquiriesScreenPreview(
    modifier: Modifier = Modifier
) {
    CustomerInquiriesScreen(
        onNavigationClick = {}
    )
}