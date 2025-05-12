package com.party.presentation.screen.profile_edit_tendency.component

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.party.common.utils.HeightSpacer
import com.party.common.utils.ScreenExplainArea
import com.party.common.ui.theme.BLACK
import com.party.common.ui.theme.DARK400
import com.party.common.ui.theme.GRAY200
import com.party.common.ui.theme.LIGHT300
import com.party.common.ui.theme.PRIMARY
import com.party.common.ui.theme.WHITE
import com.party.domain.model.user.detail.PersonalityList
import com.party.domain.model.user.detail.PersonalityListOption
import com.party.presentation.enum.PersonalityType

@Composable
fun FirstLevelArea(
    modifier: Modifier,
    personalityList: List<PersonalityList>,
    selectedList: List<PersonalityListOption> = emptyList(),
    onSelect: (PersonalityListOption) -> Unit,
    onReset: () -> Unit
) {
    Column(
        modifier = modifier
    ) {
        ScreenExplainArea(
            mainExplain = "가장 가깝다고 생각하는 성향은\n무엇인가요?",
            subExplain = "비슷한 성향의 파티원을 추천해 드려요."
        )
        HeightSpacer(heightDp = 12.dp)
        ResetTextAndIconArea(
            onReset = onReset
        )
        HeightSpacer(heightDp = 8.dp)
        if(personalityList.isNotEmpty()){
            val list = personalityList.filter { it.id == PersonalityType.TENDENCY.id }
            val tendencyList = list[0].personalityOptions

            tendencyList.forEachIndexed { _, personalityListOption ->
                SelectTendencyItem(
                    item = personalityListOption,
                    containerColor = if(selectedList.contains(personalityListOption)) LIGHT300 else WHITE,
                    fontWeight = if(selectedList.contains(personalityListOption)) FontWeight.SemiBold else FontWeight.Normal,
                    iconColor = if(selectedList.contains(personalityListOption)) PRIMARY else GRAY200,
                    textColor = if(selectedList.contains(personalityListOption)) DARK400 else BLACK,
                    onSelect = onSelect
                )
                HeightSpacer(heightDp = 8.dp)
            }
        }
    }
}