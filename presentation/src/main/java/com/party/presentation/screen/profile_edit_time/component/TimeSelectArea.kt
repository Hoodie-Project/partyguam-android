package com.party.presentation.screen.profile_edit_time.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircleOutline
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.party.common.utils.WidthSpacer
import com.party.common.utils.fs
import com.party.common.utils.noRippleClickable
import com.party.guam.design.BLACK
import com.party.guam.design.COMPONENT_AREA_HEIGHT
import com.party.guam.design.DARK400
import com.party.guam.design.GRAY200
import com.party.guam.design.LARGE_CORNER_SIZE
import com.party.guam.design.LIGHT300
import com.party.guam.design.PRIMARY
import com.party.guam.design.T3
import com.party.guam.design.WHITE
import com.party.domain.model.user.detail.PersonalityList
import com.party.domain.model.user.detail.PersonalityListOption
import com.party.guam.design.B2
import com.party.presentation.enum.PersonalityType
import com.party.presentation.screen.profile_edit_time.ProfileEditTimeState

@Composable
fun TimeSelectArea(
    personalityList: List<PersonalityList>,
    selectedList: List<PersonalityListOption>,
    onSelect: (PersonalityListOption) -> Unit
) {
    if(personalityList.isNotEmpty()){
        val list = personalityList.filter { it.id == PersonalityType.TIME.id }
        val timeList = list[0].personalityOptions
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            itemsIndexed(
                items = timeList,
                key =  { index, _ ->
                    index
                }
            ){ _, item ->
                TimeSelectAreaItem(
                    item = item,
                    containerColor = if(selectedList.contains(item)) LIGHT300 else WHITE,
                    fontWeight = if(selectedList.contains(item)) FontWeight.SemiBold else FontWeight.Normal,
                    iconColor = if(selectedList.contains(item)) PRIMARY else GRAY200,
                    textColor = if(selectedList.contains(item)) DARK400 else BLACK,
                    onSelect = onSelect
                )
            }
        }
    }
}

@Composable
private fun TimeSelectAreaItem(
    item: PersonalityListOption,
    containerColor: Color,
    fontWeight: FontWeight,
    iconColor: Color,
    textColor: Color,
    onSelect: (PersonalityListOption) -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(COMPONENT_AREA_HEIGHT)
            .noRippleClickable {
                onSelect(item)
            },
        shape = RoundedCornerShape(LARGE_CORNER_SIZE),
        border = BorderStroke(1.dp, GRAY200),
        elevation = CardDefaults.cardElevation(2.dp),
        colors = CardDefaults.cardColors(
            containerColor = containerColor
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ){
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 20.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    imageVector = Icons.Default.CheckCircleOutline,
                    contentDescription = "check",
                    tint = iconColor,
                    modifier = Modifier
                        .size(20.dp)
                )
                WidthSpacer(widthDp = 6.dp)
                Text(
                    text = item.content,
                    fontWeight = fontWeight,
                    fontSize = fs(T3),
                    color = textColor
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TimeSelectAreaPreview() {
    TimeSelectArea(
        personalityList = ProfileEditTimeState().personalityList,
        selectedList = emptyList(),
        onSelect = {}
    )
}