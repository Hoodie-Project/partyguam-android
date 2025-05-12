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
fun ThirdLevelArea(
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
            mainExplain = "아래 항목 중\n도전하고 싶은 것은 무엇인가요?",
            subExplain = "최대 2개까지 선택할 수 있어요."
        )
        HeightSpacer(heightDp = 12.dp)
        ResetTextAndIconArea(
            onReset = onReset
        )
        HeightSpacer(heightDp = 8.dp)
        if(personalityList.isNotEmpty()){
            val list = personalityList.filter { it.id == PersonalityType.CHALLENGE.id }
            val tendencyList = list[0].personalityOptions

            tendencyList.forEachIndexed { index, personalityListOption ->
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