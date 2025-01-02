package com.party.presentation.screen.profile_edit_tendency.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.party.common.HeightSpacer
import com.party.common.component.profileEditTendencyTabList
import com.party.domain.model.user.detail.PersonalityListOption
import com.party.presentation.screen.profile_edit_tendency.ProfileEditTendencyState

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TendencyArea(
    state: ProfileEditTendencyState,
    selectedTabText: String,
    onSelectTendency: (PersonalityListOption) -> Unit,
    onSelectConfidence: (PersonalityListOption) -> Unit,
    onSelectChallenge: (PersonalityListOption) -> Unit,
    onResetFirstArea: () -> Unit,
    onResetSecondArea: () -> Unit,
    onResetThirdArea: () -> Unit
) {
    val scrollState = rememberScrollState()

    val bringIntoViewRequesters = remember { List(3) { BringIntoViewRequester() } }

    val tabIndex = profileEditTendencyTabList.indexOf(selectedTabText)
    if (tabIndex in bringIntoViewRequesters.indices) {
        LaunchedEffect(key1 = selectedTabText) {
            bringIntoViewRequesters[tabIndex].bringIntoView()
        }
    }

    Column(
        modifier = Modifier
            .verticalScroll(scrollState)
    ) {
        FirstLevelArea(
            modifier = Modifier
                .bringIntoViewRequester(bringIntoViewRequesters[0]),
            personalityList = state.personalityList,
            selectedList = state.selectedTendencyList,
            onSelect = onSelectTendency,
            onReset = onResetFirstArea
        )
        HeightSpacer(heightDp = 80.dp)
        SecondLevelArea(
            modifier = Modifier
                .bringIntoViewRequester(bringIntoViewRequesters[1]),
            personalityList = state.personalityList,
            selectedList = state.selectedConfidenceList,
            onSelect = onSelectConfidence,
            onReset = onResetSecondArea
        )
        HeightSpacer(heightDp = 80.dp)
        ThirdLevelArea(
            modifier = Modifier
                .bringIntoViewRequester(bringIntoViewRequesters[2]),
            personalityList = state.personalityList,
            selectedList = state.selectedChallengeList,
            onSelect = onSelectChallenge,
            onReset = onResetThirdArea
        )
        HeightSpacer(heightDp = 160.dp)
    }


}