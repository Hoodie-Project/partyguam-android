package com.party.presentation.screen.state

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.party.common.component.searchTabList
import com.party.common.component.stateTabList
import com.party.common.ui.theme.MEDIUM_PADDING_SIZE
import com.party.common.ui.theme.WHITE
import com.party.presentation.screen.state.component.MyPartyArea
import com.party.presentation.screen.state.component.MyRecruitmentArea
import com.party.presentation.screen.state.component.StateScaffoldArea
import com.party.presentation.screen.state.component.StateTabArea

@Composable
fun StateScreen(
    modifier: Modifier = Modifier
) {
    var selectedTabText by remember {
        mutableStateOf(stateTabList[0])
    }

    StateScreenContent(
        selectedTabText = selectedTabText,
        onTabClick = { selectedTabText = it }
    )
}

@Composable
fun StateScreenContent(
    selectedTabText: String,
    onTabClick: (String) -> Unit,
) {

    Scaffold(
        topBar = {
            StateScaffoldArea(
                onGoToSearch = {},
                onGoToAlarm = {}
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
            StateTabArea(
                searchTabList = stateTabList,
                selectedTabText = selectedTabText,
                onTabClick = onTabClick
            )

            if(selectedTabText == stateTabList[0]){
                MyPartyArea()
            } else {
                MyRecruitmentArea()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun StateScreenContentPreview(
    modifier: Modifier = Modifier
) {
    StateScreenContent(
        selectedTabText = stateTabList[0],
        onTabClick = {}
    )
}