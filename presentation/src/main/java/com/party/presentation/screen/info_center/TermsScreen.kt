package com.party.presentation.screen.info_center

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
import com.party.common.utils.HeightSpacer
import com.party.guam.design.MEDIUM_PADDING_SIZE
import com.party.guam.design.WHITE
import com.party.presentation.screen.info_center.component.TermsContent
import com.party.presentation.screen.info_center.component.TermsScaffoldArea
import com.party.presentation.screen.info_center.component.terms.TermsAreaItem
import com.party.presentation.screen.info_center.component.terms.TermsSecondArea

@Composable
fun TermsScreenRoute(
    navController: NavHostController,
) {
    TermsScreen(
        onNavigationClick = { navController.popBackStack() }
    )
}

@Composable
private fun TermsScreen(
    onNavigationClick: () -> Unit,
) {
    val scrollState = rememberScrollState()
    Scaffold(
        topBar = {
            TermsScaffoldArea(
                title = "이용약관",
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
            TermsAreaItem(
                title = TermsContent.FIRST.title,
                content = TermsContent.FIRST.description
            )
            HeightSpacer(heightDp = 60.dp)
            TermsSecondArea()
            HeightSpacer(heightDp = 60.dp)
            TermsAreaItem(
                title = TermsContent.THIRD.title,
                content = TermsContent.THIRD.description
            )
            HeightSpacer(heightDp = 60.dp)
            TermsAreaItem(
                title = TermsContent.FOURTH.title,
                content = TermsContent.FOURTH.description
            )
            HeightSpacer(heightDp = 60.dp)
            TermsAreaItem(
                title = TermsContent.FIFTH.title,
                content = TermsContent.FIFTH.description
            )
            HeightSpacer(heightDp = 60.dp)
            TermsAreaItem(
                title = TermsContent.SIXTH.title,
                content = TermsContent.SIXTH.description
            )
            HeightSpacer(heightDp = 60.dp)
            TermsAreaItem(
                title = TermsContent.SEVENTH.title,
                content = TermsContent.SEVENTH.description
            )
            HeightSpacer(heightDp = 60.dp)
            TermsAreaItem(
                title = TermsContent.EIGHTH.title,
                content = TermsContent.EIGHTH.description
            )
            HeightSpacer(heightDp = 60.dp)
            TermsAreaItem(
                title = TermsContent.NINTH.title,
                content = TermsContent.NINTH.description
            )
            HeightSpacer(heightDp = 60.dp)
            TermsAreaItem(
                title = TermsContent.TENTH.title,
                content = TermsContent.TENTH.description
            )
            HeightSpacer(heightDp = 60.dp)
            TermsAreaItem(
                title = TermsContent.ELEVENTH.title,
                content = TermsContent.ELEVENTH.description
            )
            HeightSpacer(heightDp = 60.dp)
            TermsAreaItem(
                title = TermsContent.TWELFTH.title,
                content = TermsContent.TWELFTH.description
            )
            HeightSpacer(heightDp = 60.dp)
            TermsAreaItem(
                title = TermsContent.THIRTEENTH.title,
                content = TermsContent.THIRTEENTH.description
            )
            HeightSpacer(heightDp = 60.dp)
            TermsAreaItem(
                title = TermsContent.FOURTEENTH.title,
                content = TermsContent.FOURTEENTH.description
            )
            HeightSpacer(heightDp = 60.dp)
            TermsAreaItem(
                title = TermsContent.FIFTEENTH.title,
                content = TermsContent.FIFTEENTH.description
            )
            HeightSpacer(heightDp = 60.dp)
            TermsAreaItem(
                title = TermsContent.SIXTEENTH.title,
                content = TermsContent.SIXTEENTH.description
            )
            HeightSpacer(heightDp = 60.dp)
            TermsAreaItem(
                title = TermsContent.SEVENTEENTH.title,
                content = TermsContent.SEVENTEENTH.description
            )
            HeightSpacer(heightDp = 60.dp)
            TermsAreaItem(
                title = TermsContent.EIGHTEENTH.title,
                content = TermsContent.EIGHTEENTH.description
            )
            HeightSpacer(heightDp = 60.dp)
            TermsAreaItem(
                title = TermsContent.NINETEENTH.title,
                content = TermsContent.NINETEENTH.description
            )
            HeightSpacer(heightDp = 60.dp)
            TermsAreaItem(
                title = TermsContent.LAST.title,
                content = TermsContent.LAST.description
            )
        }
    }
}